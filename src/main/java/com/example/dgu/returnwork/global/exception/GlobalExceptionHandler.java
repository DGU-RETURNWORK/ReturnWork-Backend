package com.example.dgu.returnwork.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    /**
     * 커스텀 예외 처리 (BaseException 및 하위 클래스)
     * - 비즈니스 로직에서 발생하는 모든 예외 처리
     * - ErrorCode 기반으로 일관성 있는 응답 제공
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CustomErrorResponse> handleBaseException(BaseException e) {
        ErrorCode code = e.getCode();
        logError("BaseException", code, e);

        return convert(code);
    }


    /**
     * 요청 데이터 Validation 전용 ExceptionHandler (@ModelAttribute)
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomErrorResponse> bindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return convert(CommonErrorCode.VALIDATION_ERROR, extractErrorMessage(fieldErrors));
    }

    private String extractErrorMessage(List<FieldError> fieldErrors) {
        if (fieldErrors.size() == 1) {
            return fieldErrors.get(0).getDefaultMessage();
        }

        StringBuffer buffer = new StringBuffer();
        for (FieldError error : fieldErrors) {
            buffer.append(error.getDefaultMessage()).append("\n");
        }
        return buffer.toString();
    }


    /**
     * 존재하지 않는 엔드포인트 또는 타입 변환 실패 처리
     * 잘못된 URL 요청 (404)
     * 파라미터 타입 변환 실패 (예: String -> Integer 변환 실패)
     */
    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CustomErrorResponse> handleNotFoundOrTypeMismatch(Exception e) {
        if (e instanceof MethodArgumentTypeMismatchException typeMismatch) {
            log.warn("Type conversion failed: {} -> {}", 
                typeMismatch.getValue(), typeMismatch.getRequiredType().getSimpleName());
        } else {
            log.warn("Handler not found: {}", e.getMessage());
        }
        
        return convert(CommonErrorCode.NOT_SUPPORTED_URI_ERROR);
    }

    /**
     * 지원하지 않는 HTTP 메서드 처리 (405)
     * POST만 지원하는 엔드포인트에 GET 요청을 보낸 경우
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("Method not supported: {} for {}", e.getMethod(), e.getMessage());
        return convert(CommonErrorCode.NOT_SUPPORTED_METHOD_ERROR);
    }

    /**
     * 지원하지 않는 미디어 타입 처리 (415)
     * JSON만 받는 API에 XML 데이터를 보낸 경우
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.warn("Media type not supported: {}", e.getContentType());
        return convert(CommonErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR);
    }

    /**
     * 예상치 못한 서버 오류 처리 (500)
     * 위의 핸들러들로 처리되지 않은 모든 RuntimeException
     * 최후의 보루 역할
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleUnexpectedException(RuntimeException e, HttpServletRequest request) {
        log.error("Unexpected error occurred", e);
        log.error("Request info: {} {}", request.getMethod(), request.getRequestURI());
        
        return convert(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * ErrorCode를 CustomErrorResponse로 변환 (기본 메시지)
     */
    private ResponseEntity<CustomErrorResponse> convert(ErrorCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CustomErrorResponse.from(code));
    }


    private ResponseEntity<CustomErrorResponse> convert(ErrorCode code, String message) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CustomErrorResponse.of(code, message));
    }

    /**
     * 구조화된 에러 로깅
     */
    private void logError(String exceptionType, ErrorCode code, Exception e) {
        log.warn("[{}] {} | {} | {} | Message: {}", 
                exceptionType,
                code.getStatus().value(), 
                code.getErrorCode(), 
                code.getMessage(),
                e.getMessage());
    }
}
