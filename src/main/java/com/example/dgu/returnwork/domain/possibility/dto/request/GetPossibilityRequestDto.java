package com.example.dgu.returnwork.domain.possibility.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetPossibilityRequestDto (
        @NotNull(message = "accidentId는 필수 값입니다.")
        Long accidentId,
        @NotNull(message = "surveyId는 필수 값입니다.")
        Long surveyId
) {}