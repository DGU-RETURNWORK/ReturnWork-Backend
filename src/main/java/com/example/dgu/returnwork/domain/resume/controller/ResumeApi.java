package com.example.dgu.returnwork.domain.resume.controller;

import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.SetResumeResponseDto;
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

@Tag(name = "Resume", description = "자소서 관련 API")
public interface ResumeApi {

    @Operation(
            summary = "자소서 생성 API",
            description = "직업과 자소서 기본 정보를 입력받아 자소서와 질문들을 생성하는 API입니다."
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자소서 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                        {
                            "errorCode": null,
                            "message": "OK",
                            "result": {
                                "resumeId": 1,
                                "questionCount": 3,
                                "questionResumeIds": [1, 2, 3]
                            }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "검증 실패",
                                            summary = "입력값 검증 실패",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "COMMON_002",
                                "message" : "입력값 검증에 실패했습니다"
                            }
                            """
                                    ),

                            }
                    )
            ),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰인 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "유효하지 않은 토큰",
                                    value = """
                        {
                            "status" : 401,
                            "errorCode" : "AUTH_001",
                            "message" : "유효하지 않는 토큰입니다."
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 직업의 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 직업",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "JOB_001",
                            "message" : "존재하지 않는 직업입니다."
                        }
                        """
                            )
                    )
            )
    })
    CreateResumeResponseDto CreateResume(
            @Valid @RequestBody CreateResumeRequestDto request,
            @Parameter(hidden = true) @CurrentUser User user
    );

    @Operation(
            summary = "자소서 작성 기본값 조회 API",
            description = "자소서 작성 시 사용할 기본 경력 정보를 조회하는 API입니다."
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본값 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                        {
                            "errorCode": null,
                            "message": "OK",
                            "result": {
                                "career": "1년간 요식업 근무 경험"
                            }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰인 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "유효하지 않은 토큰",
                                    value = """
                        {
                            "status" : 401,
                            "errorCode" : "AUTH_001",
                            "message" : "유효하지 않는 토큰입니다."
                        }
                        """
                            )
                    )
            )}
    )
    SetResumeResponseDto setResume(@Parameter(hidden = true) @CurrentUser User user);

}