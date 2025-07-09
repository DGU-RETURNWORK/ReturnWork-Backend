package com.example.dgu.returnwork.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyEmailRequestDto(

        @Email
        @NotBlank
        @Schema(example = "dhzktldh@gmail.com")
        String email,

        @NotBlank
        String code
) {
}
