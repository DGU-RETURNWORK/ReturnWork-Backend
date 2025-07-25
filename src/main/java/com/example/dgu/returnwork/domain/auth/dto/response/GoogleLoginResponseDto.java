package com.example.dgu.returnwork.domain.auth.dto.response;

public record GoogleLoginResponseDto(

        String status,

        String message,

        String accessToken,

        String refreshToken,

        String tempToken
){

    public static GoogleLoginResponseDto login(String accessToken, String refreshToken) {
        return new GoogleLoginResponseDto("SUCCESS", "로그인 성공", accessToken, refreshToken, null);
    }

    public static GoogleLoginResponseDto signUpNeeded(String tempToken) {
        return new GoogleLoginResponseDto(
                "SIGNUP_NEEDED",
                "추가 정보를 입력해주세요",
                null,
                null,
                tempToken);
    }
}
