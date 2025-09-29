package com.example.dgu.returnwork.domain.possibility.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.service.AccidentQueryService;
import com.example.dgu.returnwork.domain.job.NcsMajorCategory;
import com.example.dgu.returnwork.domain.possibility.dto.response.*;
import com.example.dgu.returnwork.domain.possibility.exception.PossibilityErrorCode;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.infrastructure.ai.client.OpenAiRecommendationClient;
import com.example.dgu.returnwork.domain.ncs.AllowedNcsSampler;
import com.example.dgu.returnwork.domain.ncs.NcsCatalog;
import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.service.SurveyPayloadBuilder;
import com.example.dgu.returnwork.domain.survey.service.SurveyQueryService;
import com.example.dgu.returnwork.domain.survey.service.payload.SurveyPayload;
import com.example.dgu.returnwork.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PossibilityCommandService {

    private final SurveyQueryService surveyQueryService;
    private final AccidentQueryService accidentQueryService;
    private final SurveyPayloadBuilder surveyPayloadBuilder;
    private final NcsCatalog ncsCatalog;
    private final OpenAiRecommendationClient ai;

    public GetPossibilityAndJobResponseDto getPossibility(User user, GetPossibilityRequestDto request) {

        Accident accident = accidentQueryService.findAccidentById(request.accidentId());
        Survey survey = surveyQueryService.findSurveyById(request.surveyId());

        SurveyPayload surveyPayload = surveyPayloadBuilder.build(survey);
        //필요시 accidentSummary 도입

        List<AllowedNcsSampler.NcsItem> allowedNcs = AllowedNcsSampler.pickFromNcs(
                ncsCatalog.getAll(),
                surveyPayload.domainScores(),
                100,
                buildSeedKey(user, request)
        );

        Map<String, Object> userPayload = new LinkedHashMap<>();
        userPayload.put("accident", accident);
        userPayload.put("surveyVersion", surveyPayload.surveyVersion());
        userPayload.put("domainScores", surveyPayload.domainScores());
        userPayload.put("questionMap", surveyPayload.questionMap());
        userPayload.put("allowedNcs", allowedNcs);
        userPayload.put("constraints", Map.of(
                "maxJobs", 9,
                "minFitness", 60,
                "codesMustBeFromAllowedNcs", true
        ));
        userPayload.put("scaleHint", "domainScores are in 1~5. higher is better.");

        GetLLMResponseDto llmResponse = ai.recommend(userPayload);

        List<LLMJobSummary> validJobs = filterToAllowed(llmResponse, allowedNcs);

        // 응답 형식으로 파싱

        int requestedMax = 9;
        if (validJobs.size() < requestedMax) {
            log.warn("[VALIDATE RESULT] 유효 직무 {}개(요청 {}) — 불일치 항목 드롭 후", validJobs.size(), requestedMax);
        }

        List<JobSummary> jobSummaries = validJobs.stream().map(validJob -> {
            return JobSummary.builder()
                    .jobName(validJob.jobName())
                    .jobFitness(validJob.jobFitness())
                    .jobCode(validJob.jobCode())
                    .build();
        }).toList();

        List<JobDetail> jobDetails = validJobs.stream().map(validJob -> {
            return JobDetail.builder()
                    .jobType(NcsMajorCategory.getFromJobCode(validJob.jobCode()).getDisplayName())
                    .jobName(validJob.jobName())
                    .imgUrl("imageurl")
                    .jobFitness(validJob.jobFitness())
                    .jobCode(validJob.jobCode())
                    .description(validJob.description())
                    .build();
        }).toList();

        GetPossibilityAndJobResponseDto response = GetPossibilityAndJobResponseDto.builder()
                .jobSummaries(jobSummaries)
                .jobDetails(jobDetails)
                .capabilities(llmResponse.capabilities())
                .build();

        return response;

    }

    private String buildSeedKey(User user, GetPossibilityRequestDto req) {
        String uid = (user != null && user.getId() != null) ? String.valueOf(user.getId()) : "0";
        String aid = (req != null && req.accidentId() != null) ? String.valueOf(req.accidentId()) : "0";
        String sid = (req != null && req.surveyId() != null) ? String.valueOf(req.surveyId()) : "0";
        return uid + ":" + aid + ":" + sid;
    }

    private List<LLMJobSummary> filterToAllowed(
            GetLLMResponseDto dto,
            List<AllowedNcsSampler.NcsItem> allowedNcs
    ) {
        if (dto == null || dto.llmJobSummaries() == null) return List.of();
        if (allowedNcs == null || allowedNcs.isEmpty()) return List.of();

        Map<String, String> codeToName = new HashMap<>();
        for (AllowedNcsSampler.NcsItem it : allowedNcs) {
            if (it != null && it.code() != null && it.name() != null) {
                codeToName.putIfAbsent(it.code(), it.name());
            }
        }

        List<LLMJobSummary> filtered = new ArrayList<>();
        for (var js : dto.llmJobSummaries()) {
            if (js == null) continue;
            String expected = codeToName.get(js.jobCode());
            if (expected != null && expected.equals(js.jobName())) {
                filtered.add(js);
            } else {
                log.warn("[VALIDATE DROP] code={} expectedName='{}' vs actualName='{}'",
                        js != null ? js.jobCode() : null, expected, js != null ? js.jobName() : null);
            }
        }
        return filtered;
    }

}
