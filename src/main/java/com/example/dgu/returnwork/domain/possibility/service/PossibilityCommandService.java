package com.example.dgu.returnwork.domain.possibility.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.service.AccidentQueryService;
import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.request.OpenAiRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.OpenAiResponseDto;
import com.example.dgu.returnwork.domain.possibility.exception.OpenAiErrorCode;
import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.service.SurveyPayloadBuilder;
import com.example.dgu.returnwork.domain.survey.service.SurveyQueryService;
import com.example.dgu.returnwork.domain.survey.service.payload.SurveyPayload;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.exception.BaseException;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private final SurveyPayloadBuilder surveyPayloadBuilder;

    @Value("${openai.base-url:https://api.openai.com/v1}")
    private String openAiBaseUrl;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    public GetPossibilityResponseDto getPossibility(User user, GetPossibilityRequestDto request) {

        Accident accident = accidentQueryService.findAccidentById(request.accidentId());
        Survey survey = surveyQueryService.findSurveyById(request.surveyId());

        SurveyPayload surveyPayload = surveyPayloadBuilder.build(survey);
//필요시 accidentSummary 도입

        OpenAiRequestDto.Message systemMsg = new OpenAiRequestDto.Message(
                "system",
                """
                        너는 산업재해 복귀 직무 추천 도우미다.
                        규칙:
                        - 입력으로 제공된 domainScores(1~5), accident, questionMap만 근거로 사용한다.
                        - 반드시 스키마에 정확히 맞는 JSON만 출력. 여분 텍스트/주석 금지.
                        - NCS 코드는 추정하지 말고 실제 사용되는 NCS 기반으로 답변하라.
                         - accident 정보의 injuryArea, injurySeverity를 기반으로 사용자가 사용할 수 없는 신체부위가 필요한 업무는 제외하거나 적합도에서 감점할것.
                        NCS 코드에 해당되는 직업들 중 사용자의 questionMap, accident 정보를 기반으로 사용자에게 가장 적합한 직무 추천 세가지 (jobSummaries).
                        - jobSummaries: {jobName, jobFitness(0~100), jobCode(NCS code} 객체들의 배열. 이 Job은 NCS 목록에 기반하여 답변할것.
                        - capabilities: 사용자에게 적합하거나 가능한 직무 수행 특성/역량(예: 세밀한 손작업 필요 직무, 고정밀/조립 제작 작업, 중량물 취급이 많은 직무)
                        """
        );

        // 추천 직무 개수
        Map<String, Object> userPayload = new LinkedHashMap<>();
        userPayload.put("accident", accident);
        userPayload.put("surveyVersion", surveyPayload.surveyVersion());
        userPayload.put("domainScores", surveyPayload.domainScores());
        userPayload.put("questionMap", surveyPayload.questionMap());
        userPayload.put("constraints", Map.of("maxJobs", 6, "minFitness", 60));
        userPayload.put("scaleHint", "domainScores are in 1~5. higher is better.");

        OpenAiRequestDto.Message userMsg = new OpenAiRequestDto.Message(
                "user",
                """
                        아래 입력을 바탕으로 결과 JSON을 생성해줘.
                        
                        출력 스키마:
                        {
                            "jobSummaries": [
                                {
                                    "jobName": string,
                                    "jobFitness": number(0..100),
                                    "jobCode": string,
                                }
                            ],
                            "capabilities": [ string ]
                        }
                        
                        입력 페이로드:
                        %s
                        
                        """.formatted(writeJson(userPayload))
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
            throw BaseException.type(OpenAiErrorCode.OPENAI_TIMEOUT);
        } catch (HttpStatusCodeException e) {
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
