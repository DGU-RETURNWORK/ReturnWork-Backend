package com.example.dgu.returnwork.domain.possibility.dto.response;

import java.util.List;

public record GetLLMResponseDto(
    List<LLMJobSummary> llmJobSummaries,
    List<String> capabilities
) {}