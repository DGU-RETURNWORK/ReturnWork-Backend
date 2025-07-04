package com.example.dgu.returnwork.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    
    // ===== 공통 에러 (4xx) =====
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON_002", "입력값 검증에 실패했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_003", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_004", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_005", "요청한 리소스를 찾을 수 없습니다."),
    NOT_SUPPORTED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_006", "지원하지 않는 HTTP 메서드입니다."),
    NOT_SUPPORTED_URI_ERROR(HttpStatus.NOT_FOUND, "COMMON_007", "지원하지 않는 URI입니다."),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "COMMON_008", "지원하지 않는 미디어 타입입니다."),
    
    // ===== 사용자 관련 에러 =====
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_002", "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER_003", "비밀번호가 올바르지 않습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "USER_004", "올바르지 않은 이메일 형식입니다."),
    
    // ===== 인증/인가 관련 에러 =====
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_003", "리프레시 토큰을 찾을 수 없습니다."),
    
    // ===== 파일 관련 에러 =====
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE_001", "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "FILE_002", "파일 업로드에 실패했습니다."),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "FILE_003", "지원하지 않는 파일 형식입니다."),
    
    // ===== 서버 에러 (5xx) =====
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_002", "데이터베이스 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "SERVER_003", "외부 API 호출에 실패했습니다.");
    
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
