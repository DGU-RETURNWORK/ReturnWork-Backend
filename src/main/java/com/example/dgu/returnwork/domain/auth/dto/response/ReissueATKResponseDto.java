package com.example.dgu.returnwork.domain.auth.dto.response;

public record ReissueATKResponseDto(

        String accessToken
){

    public static ReissueATKResponseDto of(String accessToken){
        return new ReissueATKResponseDto(accessToken);
    }
}
