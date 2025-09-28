package com.example.dgu.returnwork.domain.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateQuestionOrderRequestDto(
        @NotNull
        @Schema(example = "3")
        @Min(1)
        Integer questionOrder
) {
}
