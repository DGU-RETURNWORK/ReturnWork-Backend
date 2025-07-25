package com.example.dgu.returnwork.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthPasswordRequestDto(

        @NotBlank(message = "password를 입력해주세요")
        @Schema(example = "ttkdd124@")
        String password
) {
}
