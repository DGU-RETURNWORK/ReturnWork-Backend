package com.example.dgu.returnwork.global.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static BaseException type(ErrorCode errorCode) {
        return new BaseException(errorCode);
    }
    
    public ErrorCode getCode() {
        return errorCode;
    }
}
