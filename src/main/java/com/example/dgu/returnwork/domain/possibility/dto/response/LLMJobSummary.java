package com.example.dgu.returnwork.domain.possibility.dto.response;

public record LLMJobSummary(
        String jobName,
        int jobFitness,
        String jobCode,
        String description
) {
}
