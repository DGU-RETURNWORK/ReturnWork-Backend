package com.example.dgu.returnwork.domain.possibility.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.service.AccidentQueryService;
import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.request.OpenAiRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.OpenAiResponseDto;
import com.example.dgu.returnwork.domain.possibility.exception.OpenAiErrorCode;
import com.example.dgu.returnwork.domain.survey.Survey;
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
    private final OpenAiRequestDto.Text textBean;
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
                        반드시 스키마에 정확히 맞는 JSON만 출력해. 여분의 텍스트/설명 금지.
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
                textBean,
                1024
        );

        try {
            var resp = restTemplate.postForEntity(
                    openAiBaseUrl + "/responses",
                    req,
                    OpenAiResponseDto.class
            );

            log.info("[OpenAI 응답 코드]: {}", resp.getStatusCode());
            log.info("[OpenAI 응답 바디]: {}", resp.getBody());

            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                throw BaseException.type(OpenAiErrorCode.OPENAI_CALL_FAILED);
            }

            String jsonPayload = resp.getBody().unifiedText();
            if (jsonPayload == null || jsonPayload.isBlank()) {
                throw BaseException.type(OpenAiErrorCode.OPENAI_EMPTY_RESPONSE);
            }

            return objectMapper.readValue(jsonPayload, GetPossibilityResponseDto.class);
        } catch (ResourceAccessException e) {
            //throw new IllegalStateException("OpenAI 통신 실패(네트워크/타임아웃): " + e.getMessage(), e);
            throw BaseException.type(OpenAiErrorCode.OPENAI_TIMEOUT);
        } catch (HttpStatusCodeException e) {
            //throw new IllegalStateException("OpenAI 상태 오류: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            var status = e.getStatusCode();

            if (status.is4xxClientError()) {
                switch (status.value()) {
                    case 401 -> throw BaseException.type(OpenAiErrorCode.OPENAI_UNAUTHORIZED);
                    case 403 -> throw BaseException.type(OpenAiErrorCode.OPENAI_FORBIDDEN);
                    case 404 -> throw BaseException.type(OpenAiErrorCode.OPENAI_NOT_FOUND);
                    default -> throw BaseException.type(OpenAiErrorCode.OPENAI_BAD_REQUEST);
                }
            } else {
                throw BaseException.type(OpenAiErrorCode.OPENAI_CALL_FAILED);
            }
        } catch (IOException e) {
            //throw new IllegalStateException("모델 JSON 파싱 실패: " + e.getMessage(), e);
            throw BaseException.type(OpenAiErrorCode.OPENAI_PARSE_ERROR);
        }
    }

    private String writeJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            return String.valueOf(o);
        }
    }
}
