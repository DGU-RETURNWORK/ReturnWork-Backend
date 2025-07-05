package com.example.dgu.returnwork.domain.region.exception;

import com.example.dgu.returnwork.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RegionErrorCode implements ErrorCode {


    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION_001", "지역을 찾을 수 없습니다");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
