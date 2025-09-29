package com.example.dgu.returnwork.domain.possibility.dto.response;

import lombok.Builder;

@Builder
public record JobSummary (
    String jobName,
    int jobFitness,
    String jobCode
){}
