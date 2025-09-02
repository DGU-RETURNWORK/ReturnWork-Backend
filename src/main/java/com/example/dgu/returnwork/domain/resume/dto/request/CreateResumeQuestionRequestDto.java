package com.example.dgu.returnwork.domain.resume.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateResumeQuestionRequestDto(
    @NotNull
    @Min(1) @Max(10)
    Integer questionOrder
) {
}
