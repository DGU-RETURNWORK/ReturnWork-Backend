package com.example.dgu.returnwork.domain.possibility.dto.response;

import java.util.List;

public record GetPossibilityResponseDto (
    List<JobSummary> jobSummaries,
    List<String> capabilities
) {}