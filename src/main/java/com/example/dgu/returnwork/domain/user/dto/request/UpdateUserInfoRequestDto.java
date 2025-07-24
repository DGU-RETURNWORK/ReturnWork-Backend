package com.example.dgu.returnwork.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserInfoRequestDto(

        @NotBlank
        @Schema(description = "사용자 이름", example = "추상윤")
        String name,

        @NotBlank
        @Schema(description = "생년월일", example = "2003-03-03")
        String birthday,

        @NotBlank
        @Schema(description = "전화번호", example = "010-7689-3141")
        String phoneNumber,

        @NotBlank
        @Schema(description = "경력 사항", example = "요식업 2년차")
        String career,

        @NotBlank
        @Schema(description = "거주지", example = "서울시 강서구")
        String region
) {
}
