package com.example.dgu.returnwork.domain.user.exception;


import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "USER_001", "나이는 14세 이상 100세 이하여야 합니다"),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER_002", "이미 가입된 이메일입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
