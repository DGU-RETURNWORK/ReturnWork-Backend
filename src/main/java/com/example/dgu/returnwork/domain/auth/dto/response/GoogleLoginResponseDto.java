package com.example.dgu.returnwork.domain.auth.dto.response;

public record GoogleLoginResponseDto(

        String status,

        String message,

        String accessToken,

        String refreshToken
){

    public static GoogleLoginResponseDto login(String accessToken, String refreshToken) {
        return new GoogleLoginResponseDto("SUCCESS", "로그인 성공", accessToken, refreshToken);
    }

    public static GoogleLoginResponseDto signUpNeeded() {
        return new GoogleLoginResponseDto(
                "SIGNUP_NEEDED",
                "추가 정보를 입력해주세요",
                null,
                null);
    }
}
