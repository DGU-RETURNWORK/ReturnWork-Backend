package com.example.dgu.returnwork.domain.user.exception;


import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "USER_001", "나이는 14세 이상 100세 이하여야 합니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER_002", "패스워드가 일치하지 않습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER_003", "이미 가입된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_004", "존재하지 않는 사용자입니다."),
    INVALID_EMAIL_CODE(HttpStatus.BAD_REQUEST, "USER_005", "일치하지 않는 이메일 코드입니다."),
    EMAIL_CODE_ERROR(HttpStatus.BAD_REQUEST, "USER_006", "인증번호를 재요청해주세요.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
