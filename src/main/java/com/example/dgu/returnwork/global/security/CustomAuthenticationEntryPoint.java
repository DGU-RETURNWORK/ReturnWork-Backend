package com.example.dgu.returnwork.global.security;

import com.example.dgu.returnwork.global.exception.CommonErrorCode;
import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import com.example.dgu.returnwork.global.exception.ErrorCode;
import com.example.dgu.returnwork.global.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

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

        String token = jwtTokenProvider.resolveToken(request);

        TokenValidationResult result = jwtTokenProvider.validateToken(token);

        return switch (result) {
            case EXPIRED -> CommonErrorCode.EXPIRED_TOKEN;
            case INVALID, VALID -> CommonErrorCode.INVALID_TOKEN;
        };


    }
}
