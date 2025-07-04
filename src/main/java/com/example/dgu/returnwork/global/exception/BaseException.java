package com.example.dgu.returnwork.global.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    
    private final CommonErrorCode errorCode;

    public BaseException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static BaseException type(CommonErrorCode errorCode) {
        return new BaseException(errorCode);
    }
    
    public CommonErrorCode getCode() {
        return errorCode;
    }
}
