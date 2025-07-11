package com.example.dgu.returnwork.domain.accident.dto.request;

import com.example.dgu.returnwork.domain.accident.AccidentType;
import com.example.dgu.returnwork.domain.accident.IndustryType;
import com.example.dgu.returnwork.domain.accident.InjuryArea;
import com.example.dgu.returnwork.domain.accident.InjurySeverity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccidentRequestDto (

        @NotNull(message = "산업군은 필수입니다.")
        @Schema(example = "MANUFACTURING")
        IndustryType industryType,

        @NotNull(message = "사고 유형은 필수입니다.")
        @Schema(example = "ENTANGLED")
        AccidentType accidentType,

        @NotNull(message = "부상 부위는 필수입니다.")
        @Schema(example = "HAND")
        InjuryArea injuryArea,

        @NotNull(message = "부상 정도는 필수입니다.")
        @Schema(example = "FRACTURE")
        InjurySeverity injurySeverity,

        @Size(max = 200, message = "상세내용은 200자 이내여야 합니다.")
        @Schema(example = "손이 기계에 끼어 골절됐어요.")
        String detail

) {
}
