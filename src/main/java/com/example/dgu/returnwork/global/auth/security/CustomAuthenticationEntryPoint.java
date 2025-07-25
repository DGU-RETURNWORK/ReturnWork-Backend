package com.example.dgu.returnwork.global.auth.security;

import com.example.dgu.returnwork.domain.auth.exception.AuthErrorCode;
import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import com.example.dgu.returnwork.global.exception.ErrorCode;
import com.example.dgu.returnwork.global.auth.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorCode errorCode = determineErrorCode(request);


        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CustomErrorResponse.from(errorCode)));


    }

    private ErrorCode determineErrorCode(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        TokenValidationResult result = jwtUtil.validateToken(token);

        return switch (result) {
            case EXPIRED -> AuthErrorCode.EXPIRED_TOKEN;
            case INVALID, VALID -> AuthErrorCode.INVALID_TOKEN;
        };


    }
}
