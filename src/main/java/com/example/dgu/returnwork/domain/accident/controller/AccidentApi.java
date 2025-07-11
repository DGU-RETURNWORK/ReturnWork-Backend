package com.example.dgu.returnwork.domain.accident.controller;

import com.example.dgu.returnwork.domain.accident.dto.request.CreateAccidentRequestDto;
import com.example.dgu.returnwork.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Accident", description = "사고 관련 API")
public interface AccidentApi {

    @Operation(
            summary = "사고 정보 입력",
            description = "사고 정보 입력 API 입니다."
    )
    ApiResponse<Void> createAccident(@RequestBody @Valid CreateAccidentRequestDto request);

}
