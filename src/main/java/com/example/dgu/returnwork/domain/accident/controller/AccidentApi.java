package com.example.dgu.returnwork.domain.accident.controller;

import com.example.dgu.returnwork.domain.accident.dto.request.CreateAccidentRequestDto;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.annotation.CurrentUser;
import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Accident", description = "사고 관련 API")
public interface AccidentApi {

    @Operation(
            summary = "사고 정보 입력",
            description = "사고 정보 입력 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사고 정보 등록 성공",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                        examples = @ExampleObject(
                                name = "성공 응답",
                                value = """
                                        {
                                            "errorCode": null,
                                            "message": "OK",
                                            "result": null
                                        }                                    
                                        """
                        )
                )
            ),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = CustomErrorResponse.class),
                        examples = {
                            @ExampleObject(
                                    name = "입력값 검증 실패",
                                    value = """
                                    {
                                        "status": 400,
                                        "errorCode": "COMMON_002",
                                        "message": "입력값 검증에 실패했습니다."
                                    }
                                    """
                            ),
                            @ExampleObject(
                                    name = "JSON 파싱 실패",
                                    value = """
                                            {
                                                "status": 400,
                                                "errorCode": "COMMON_002",
                                                "message": "요청 형식이 잘못되었습니다. 입력값을 확인해 주세요."
                                            }
                                            """
                            )

                        }
                )
            ),
            @ApiResponse(responseCode = "401", description = "JWT 인증 실패 (유효하지 않거나 만료된 토큰)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                @ExampleObject(
                                        name = "유효하지 않은 토큰",
                                        value = """
                                            {
                                                "status": 401,
                                                "errorCode": "AUTH_001",
                                                "message": "유효하지 않은 토큰입니다."
                                            }
                                            """
                                ),
                                @ExampleObject(
                                        name = "만료된 토큰",
                                        value = """
                                                {
                                                    "status": 401,
                                                    "errorCode": "AUTH_002",
                                                    "message": "만료된 토큰입니다."
                                                }
                                                """
                                )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 사용자",
                                    value = """
                                        {
                                            "status": 404,
                                            "errorCode": "USER_004",
                                            "message": "존재하지 않는 사용자입니다."
                                        }
                                        """
                            )
                    )
            )
    })
    @SecurityRequirement(name = "JWT")
    com.example.dgu.returnwork.global.response.ApiResponse<Void> createAccident(
            @Parameter(hidden = true) @CurrentUser User user,
            @RequestBody @Valid CreateAccidentRequestDto request
    );

}
