package com.example.dgu.returnwork.domain.auth.dto.response;

public record LoginUserResponseDto (

        String accessToken,

        String refreshToken
)
{
    public static LoginUserResponseDto of(String accessToken, String refreshToken) {
        return new LoginUserResponseDto(accessToken, refreshToken);
    }


}
