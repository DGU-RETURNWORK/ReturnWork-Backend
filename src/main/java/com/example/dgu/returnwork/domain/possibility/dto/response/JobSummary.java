package com.example.dgu.returnwork.domain.possibility.dto.response;

public record JobSummary(
        String jobName,
        int jobFitness,
        String jobCode
) {
}
