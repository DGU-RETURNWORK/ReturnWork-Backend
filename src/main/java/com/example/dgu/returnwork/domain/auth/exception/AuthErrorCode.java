package com.example.dgu.returnwork.domain.auth.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {


    // ===== 인증/인가 관련 에러 =====  //
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_003", "리프레시 토큰을 찾을 수 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "AUTH_004","인증되지 않은 사용자입니다."),

    // ==== OAuth 관련 에러 ==== //
    GOOGLE_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_005", "사용자 정보를 찾을 수 없습니다."),
    GOOGLE_OAUTH_FAILED(HttpStatus.BAD_REQUEST, "AUTH_006", "Google 사용자 정보 조회 실패");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
