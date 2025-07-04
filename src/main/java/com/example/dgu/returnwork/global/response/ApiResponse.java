package com.example.dgu.returnwork.global.response;

import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"errorCode", "message", "result"})
public class ApiResponse<T> {

    private String errorCode;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, "OK", data);
    }

    public static <T> ApiResponse<T> fail(CustomErrorResponse customErrorResponse) {
        return new ApiResponse<>(customErrorResponse.getErrorCode(), customErrorResponse.getMessage(), null);
    }


}
