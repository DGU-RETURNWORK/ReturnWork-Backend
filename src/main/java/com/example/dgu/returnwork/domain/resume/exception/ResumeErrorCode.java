package com.example.dgu.returnwork.domain.resume.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResumeErrorCode implements ErrorCode{

    NOT_FOUND_RESUME(HttpStatus.NOT_FOUND, "RESUME_001", "자소서를 찾을 수 없습니다."),
    ALREADY_COMPLETED_RESUME(HttpStatus.BAD_REQUEST, "RESUME_002", "이미 작성 완료된 자소서입니다."),
    NOT_FOUND_RESUME_QUESTION(HttpStatus.NOT_FOUND, "RESUME_003", "자소서 세부문항을 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
