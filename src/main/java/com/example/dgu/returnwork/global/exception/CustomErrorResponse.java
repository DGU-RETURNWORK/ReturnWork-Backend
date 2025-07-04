package com.example.dgu.returnwork.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {

    private int status;
    private String errorCode;
    private String message;

    private CustomErrorResponse(ErrorCode code){
        this.status = code.getStatus().value();
        this.errorCode = code.getErrorCode();
        this.message = code.getMessage();
    }

    public static CustomErrorResponse from(ErrorCode code){
        return new CustomErrorResponse(code);
    }

    public static CustomErrorResponse of(ErrorCode errorCode, String message){
        return new CustomErrorResponse(errorCode.getStatus().value(), errorCode.getErrorCode(), message);
    }
}