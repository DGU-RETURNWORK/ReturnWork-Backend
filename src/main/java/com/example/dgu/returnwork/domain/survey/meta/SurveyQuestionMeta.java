package com.example.dgu.returnwork.domain.survey.meta;

public record SurveyQuestionMeta(
        int id,
        String questionText,
        String label,
        String metricKey,
        String domain,
        int min,
        int max,
        double weight
) {
}
