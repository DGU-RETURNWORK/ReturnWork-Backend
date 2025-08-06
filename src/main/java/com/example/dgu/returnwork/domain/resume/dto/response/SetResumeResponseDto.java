package com.example.dgu.returnwork.domain.resume.dto.response;

public record SetResumeResponseDto(

        String career

) {
    public static SetResumeResponseDto from(String career){
        return new SetResumeResponseDto(career);
    };
}
