package com.example.dgu.returnwork.domain.survey.dto.response;

import com.example.dgu.returnwork.domain.survey.Survey;
import lombok.Builder;

@Builder
public record GetSurveyResponseDto(

        String message,

        Integer answer1,

        Integer answer2,

        Integer answer3,

        Integer answer4,

        Integer answer5,

        Integer answer6,

        Integer answer7,

        Integer answer8,

        Integer answer9,

        Integer answer10
) {
    public static GetSurveyResponseDto newSurvey(Survey survey){
        return GetSurveyResponseDto.builder()
                .message("임시저장된 설문조사가 존재합니다.")
                .answer1(survey.getA1())
                .answer2(survey.getA2())
                .answer3(survey.getA3())
                .answer4(survey.getA4())
                .answer5(survey.getA5())
                .answer6(survey.getA6())
                .answer7(survey.getA7())
                .answer8(survey.getA8())
                .answer9(survey.getA9())
                .answer10(survey.getA10())
                .build();
    }

    public static GetSurveyResponseDto notFoundSurvey(){
        return GetSurveyResponseDto.builder()
                .message("신규 설문조사입니다.")
                .build();
    }
}
