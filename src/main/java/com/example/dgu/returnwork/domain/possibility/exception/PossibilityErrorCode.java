package com.example.dgu.returnwork.domain.possibility.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PossibilityErrorCode implements ErrorCode {

    NCS_SAMPLING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "POSSIBILITY_001", "NCS 샘플링 과정에서 오류가 발생했습니다."),
    RESULT_INTEGRITY_VIOLATION(HttpStatus.BAD_GATEWAY, "POSSIBILITY_002", "AI 응답이 허용된 NCS 목록과 일치하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
