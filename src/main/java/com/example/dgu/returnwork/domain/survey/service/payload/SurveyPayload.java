package com.example.dgu.returnwork.domain.survey.service.payload;

import java.util.Map;

public record SurveyPayload (
        int surveyVersion,
        Map<String, Double> domainScores,
        Map<Integer, Map<String, Object>> questionMap
) {
}
