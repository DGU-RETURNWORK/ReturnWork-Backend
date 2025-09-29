package com.example.dgu.returnwork.domain.possibility.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetPossibilityAndJobResponseDto (

    List<JobSummary> jobSummaries,
    List<JobDetail> jobDetails,
    List<String> capabilities
){}
