package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserApi {

    @Operation(
            summary = "회원가입",
            description = "회원가입 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
            examples = @ExampleObject(
                    name = "성공 응답",
                    value = """
            {
              "errorCode" : null,
              "message" : "OK",
              "result" : null
            
            }
            """
            ))),
            @ApiResponse(responseCode = "400", description = "잘못된 값 요청",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CustomErrorResponse.class),
            examples = @ExampleObject(
                name = "검증 실패",
                value = """
                {
                    "status" : 400,
                    "errorCode" : "COMMON_002",
                    "message" : "입력값 검증에 실패했습니다"
            
            }
            """
                    ))),

    })
    public void signUp(@RequestBody @Valid SignUpRequestDto request);

}
