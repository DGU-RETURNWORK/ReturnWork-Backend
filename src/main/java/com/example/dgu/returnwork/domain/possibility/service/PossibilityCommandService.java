package com.example.dgu.returnwork.domain.possibility.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.exception.AccidentErrorCode;
import com.example.dgu.returnwork.domain.accident.repository.AccidentRepository;
import com.example.dgu.returnwork.domain.accident.service.AccidentQueryService;
import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.request.OpenAiRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.exception.SurveyErrorCode;
import com.example.dgu.returnwork.domain.survey.repository.SurveyRepository;
import com.example.dgu.returnwork.domain.survey.service.SurveyQueryService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PossibilityCommandService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OpenAiRequestDto.ResponseFormat responseFormat;
    private final SurveyQueryService surveyQueryService;
    private final AccidentQueryService accidentQueryService;

    @Value("${openai.base-url:https://api.openai.com/v1}")
    private String openAiBaseUrl;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    public GetPossibilityResponseDto getPossibility(User user, GetPossibilityRequestDto request) {

        //적합도 기준?
        Accident accident = accidentQueryService.findAccidentById(request.accidentId());
        Survey survey = surveyQueryService.findSurveyById(request.surveyId());

        var systemMsg = new OpenAiRequestDto.Message(
                "system",
                """
                        너는 산업재해 사고 정보와 사용자의 설문 결과를 바탕으로
                        적합한 직무와 사용자가 가능한 직무 수행 특성/역량을 산출한다.
                        반드시 스키마에 '정확히' 맞는 JSON만 출력해. 여분의 텍스트/설명 금지.
                        - jobSummaries: {jobName, jobFitness(0~100), jobCode(NCS code} 객체들의 배열. 이 Job은 NCS 목록에 기반하여 답변할것.
                        - capabilities: 사용자에게 적합하거나 가능한 직무 수행 특성/역량(예: 세밀한 손작업 필요 직무, 고정밀/조립 제작 작업, 중량물 취급이 많은 직무)
                        """
        );

        var userMsg = new OpenAiRequestDto.Message(
                "user",
                """
                        [사고 객체 JSON]
                        %s
                        
                        [설문 객체 JSON]
                        %s
                        
                        위 정보를 바탕으로 스키마에 맞춰 결과를 출력해.
                        """.formatted(writeJson(accident), writeJson(survey))
        );

        var req = new OpenAiRequestDto(
                model,
                List.of(systemMsg, userMsg),
                //responseFormat,
                1024
        );

        try {
            var resp = restTemplate.postForEntity(
                    openAiBaseUrl + "/responses",
                    req,
                    String.class
            );

            log.info("[OpenAI 응답 코드]: {}", resp.getStatusCode());
            log.info("[OpenAI 응답 바디]: {}", resp.getBody());

            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                throw new IllegalStateException("OpenAI call failed: " + resp.getStatusCode());
            //예외처리 다시
            }

            String raw = resp.getBody();

            if (raw == null || raw.isBlank()) {
                throw new IllegalStateException("OpenAI 응답이 비어 있음");
            }

            //String cleaned = raw.strip().replaceAll("^`+|`+$", "");

            //String jsonPayload = extractJsonString(cleaned);

            String jsonPayload = extractJsonString(resp.getBody());

            return objectMapper.readValue(jsonPayload, GetPossibilityResponseDto.class);
        } catch (ResourceAccessException e) {
            throw new IllegalStateException("OpenAI 통신 실패(네트워크/타임아웃): " + e.getMessage(), e);
        } catch (HttpStatusCodeException e) {
            throw new IllegalStateException("OpenAI 상태 오류: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (IOException e) {
            throw new IllegalStateException("모델 JSON 파싱 실패: " + e.getMessage(), e);
        }
    }

    private String writeJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            return String.valueOf(o);
        }
    }

    private String extractJsonString(String rawBody) {
        try {
            JsonNode root = objectMapper.readTree(rawBody);

            JsonNode output = root.path("output");
            if (output.isArray() && output.size() > 0) {
                JsonNode content = output.get(0).path("content");
                if (content.isArray() && content.size() > 0) {
                    JsonNode textNode = content.get(0).path("text");
                    if (textNode.isTextual() && !textNode.asText().isBlank()) {
                        String rawText = textNode.asText();
                        log.info("[모델 응답 텍스트]: {}", rawText);

                        String cleaned = rawText
                                .replaceAll("^```json\\s*", "")
                                .replaceAll("```$", "")
                                .trim();

                        log.info("[정제된 JSON 텍스트]: {}", cleaned);
                        return cleaned;
                    }
                }
            }

            if (root.has("jobSummaries") && root.has("capabilities")) {
                return objectMapper.writeValueAsString(root);
            }

            throw new IllegalStateException("모델 JSON 페이로드를 찾지 못했습니다: " + rawBody);
        } catch (Exception e) {
            throw new IllegalStateException("OpenAI 응답 파싱 실패: " + e.getMessage(), e);
        }
    }

}
