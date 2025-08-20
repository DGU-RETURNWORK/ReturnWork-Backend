package com.example.dgu.returnwork.domain.possibility.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetPossibilityResponseDto (
        @JsonProperty("jobSummaries")
    List<JobSummary> jobSummaries,
    List<String> capabilities
) {}