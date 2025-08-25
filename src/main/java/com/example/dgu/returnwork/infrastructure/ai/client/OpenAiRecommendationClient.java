package com.example.dgu.returnwork.infrastructure.ai.client;

import com.example.dgu.returnwork.infrastructure.ai.dto.request.OpenAiRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
import com.example.dgu.returnwork.infrastructure.ai.dto.response.OpenAiResponseDto;
import com.example.dgu.returnwork.infrastructure.ai.exception.OpenAiErrorCode;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiRecommendationClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OpenAiRequestDto.Text textBean;

    @Value("${openai.base-url:https://api.openai.com/v1}")
    private String openAiBaseUrl;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    public GetPossibilityResponseDto recommend(Map<String, Object> userPayload) {
        OpenAiRequestDto.Message systemMsg = new OpenAiRequestDto.Message(
                "system",
                """
                        너는 산업재해 복귀 직무 추천 도우미다.
                        절대 규칙:
                        - 입력으로 제공된 domainScores(1~5), accident, questionMap, allowedNcs만 근거로 사용한다.
                        - 반드시 스키마에 정확히 맞는 JSON만 출력. 여분 텍스트/주석 금지.
                        - NCS 코드는 추정하지 말고 반드시 allowedNcs에 포함된 code 중에서만 선택하여 답변하라.
                        - accident 정보의 injuryArea, injurySeverity를 기반으로 해당 부위 사용이 필수인 업무는 jobSummaries에서 제외하거나 적합도에서 감점할것.
                        - accident 정보의 injuryArea, injurySeverity를 기반으로 사용이 불편한 신체 부위를 고려하여 capabilities를 도출할것. 예를 들면, 손(HAND)이 불편한 사람은 손으로 하는 직무를 할 수 없음. 유지보수는 손으로 하는 직무임. 
                        - jobSummaries는 allowedNcs의 {code, name}와 일치해야한다. (코드-이름 매칭 불일치 금지)
                       
                        NCS 코드에 해당되는 직업들 중 사용자의 questionMap, accident 정보를 기반으로 사용자에게 가장 적합한 직무 추천 세가지 (jobSummaries).
                        - jobSummaries: {jobName, jobFitness(0~100), jobCode(NCS code} 객체들의 배열. 이 Job은 NCS 목록에 기반하여 답변할것. 적합도가 높은 순서대로 jobSummaries 세 개 도출.
                        - capabilities: 사용자에게 적합하거나 가능한 직무 수행 특성/역량을 세 개 도출. "~가 필요한 직무", "~제작 작업", "~취급 직무", "~업무" 등의 형식으로 도출.
                        """
        );

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

            // 필요 시 허용 코드 및 이름 검증 함수 추가

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
