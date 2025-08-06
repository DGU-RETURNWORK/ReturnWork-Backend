package com.example.dgu.returnwork.domain.job.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum JobErrorCode implements ErrorCode {


    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "JOB_001", "직업을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
