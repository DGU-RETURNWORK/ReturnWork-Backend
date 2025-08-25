package com.example.dgu.returnwork.domain.possibility.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.service.AccidentQueryService;
import com.example.dgu.returnwork.infrastructure.ai.client.OpenAiRecommendationClient;
import com.example.dgu.returnwork.domain.ncs.AllowedNcsSampler;
import com.example.dgu.returnwork.domain.ncs.NcsCatalog;
import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
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


    public GetPossibilityResponseDto getPossibility(User user, GetPossibilityRequestDto request) {

        Accident accident = accidentQueryService.findAccidentById(request.accidentId());
        Survey survey = surveyQueryService.findSurveyById(request.surveyId());

        SurveyPayload surveyPayload = surveyPayloadBuilder.build(survey);
        //필요시 accidentSummary 도입

        List<Map<String, String>> allowedNcs = AllowedNcsSampler.pickFromNcs(
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
                "maxJobs", 6,
                "minFitness", 60,
                "codesMustBeFromAllowedNcs", true
        ));
        userPayload.put("scaleHint", "domainScores are in 1~5. higher is better.");

        return ai.recommend(userPayload);

    }

    private String buildSeedKey(User user, GetPossibilityRequestDto req) {
        String uid = (user != null && user.getId() != null) ? String.valueOf(user.getId()) : "0";
        String aid = (req != null && req.accidentId() != null) ? String.valueOf(req.accidentId()) : "0";
        String sid = (req != null && req.surveyId() != null) ? String.valueOf(req.surveyId()) : "0";
        return uid + ":" + aid + ":" + sid;
    }
}
