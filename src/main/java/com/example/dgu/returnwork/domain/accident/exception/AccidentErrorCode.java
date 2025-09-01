package com.example.dgu.returnwork.domain.accident.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccidentErrorCode implements ErrorCode {

    ACCIDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCIDENT_001", "해당 사고 정보가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

}


