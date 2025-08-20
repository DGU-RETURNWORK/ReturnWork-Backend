package com.example.dgu.returnwork.domain.possibility.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JobSummary(
        @JsonProperty("jobName")
        String jobName,
        @JsonProperty("jobFitness")
        int jobFitness,
        @JsonProperty("jobCode")
        String jobCode
) {
}
