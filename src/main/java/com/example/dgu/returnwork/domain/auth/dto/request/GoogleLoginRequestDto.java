package com.example.dgu.returnwork.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequestDto(

        @NotBlank
        String accessToken
)
{
}
