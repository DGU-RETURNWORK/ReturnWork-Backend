package com.example.dgu.returnwork.domain.survey.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SurveyErrorCode implements ErrorCode {

    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "SURVEY_001", "해당 설문 결과가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
