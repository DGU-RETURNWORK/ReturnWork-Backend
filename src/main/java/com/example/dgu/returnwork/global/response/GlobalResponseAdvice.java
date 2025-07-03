package com.example.dgu.returnwork.global.response;

import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        
        // 제외할 타입들
        return !parameterType.equals(ApiResponse.class) &&      // 이미 래핑됨
               !ResponseEntity.class.isAssignableFrom(parameterType) &&  // ResponseEntity와 그 하위 타입들
               !parameterType.equals(String.class) &&           // Swagger UI 관련
               !parameterType.getName().startsWith("org.springframework"); // Spring 내부 클래스들
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        
        if (body instanceof CustomErrorResponse) {
            return ApiResponse.fail((CustomErrorResponse)body);
        }
        
        return ApiResponse.success(body);
    }
}
