package com.example.dgu.returnwork.infrastructure.ai.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OpenAiErrorCode implements ErrorCode {

    OPENAI_CALL_FAILED(HttpStatus.BAD_GATEWAY, "OPENAI_001", "OpenAi 호출에 실패하였습니다."),
    OPENAI_EMPTY_RESPONSE(HttpStatus.BAD_GATEWAY, "OPENAI_002", "OpenAI 응답이 비어 있습니다."),
    OPENAI_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "OPENAI_003", "OpenAI 응답이 지연되었습니다."),
    OPENAI_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "OPENAI_004", "OpenAI 인증에 실패했습니다."),
    OPENAI_FORBIDDEN(HttpStatus.FORBIDDEN, "OPENAI_005", "OpenAI 호출 권한이 없습니다."),
    OPENAI_NOT_FOUND(HttpStatus.BAD_GATEWAY, "OPENAI_006", "OpenAI 업스트림에서 리소스를 찾지 못했습니다."),
    OPENAI_BAD_REQUEST(HttpStatus.BAD_REQUEST, "OPENAI_007", "요청 형식이 올바르지 않습니다."),
    OPENAI_PARSE_ERROR(HttpStatus.BAD_GATEWAY, "OPENAI_008", "OpenAI 응답 파싱에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
