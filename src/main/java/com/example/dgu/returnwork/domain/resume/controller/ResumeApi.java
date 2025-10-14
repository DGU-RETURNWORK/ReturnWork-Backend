package com.example.dgu.returnwork.domain.resume.controller;

import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeQuestionRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.GetResumeQuestionListResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    CreateResumeResponseDto createResume(
            @Valid @RequestBody CreateResumeRequestDto request,
            @Parameter(hidden = true) @CurrentUser User user
    );

    @Operation(
            summary = "자소서 작성 기본값 및 기존 자소서 조회 API",
            description = """
                자소서 작성 시 사용할 기본 경력 정보와 기존 DRAFT 상태 자소서 목록을 조회하는 API입니다.
                
                ## 분기 처리 로직:
                - **isNew = true**: 기존 DRAFT 자소서가 없는 경우 → 바로 새 자소서 생성 가능
                - **isNew = false**: 기존 DRAFT 자소서가 1개 이상 있는 경우 → 기존 자소서 목록에서 선택하거나 새로 생성
                
                ## 프론트엔드 처리 방법:
                1. isNew가 true인 경우: 자소서 생성 폼으로 바로 이동
                2. isNew가 false인 경우: 기존 자소서 선택 화면 표시 후 사용자 선택에 따라 분기
                """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본값 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "신규 사용자 (기존 자소서 없음)",
                                            summary = "DRAFT 상태 자소서가 없는 경우",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                        "career": "1년간 요식업 근무 경험",
                                        "isNew": true,
                                        "draftResumeList": null
                                    }
                                }
                                """
                                    ),
                                    @ExampleObject(
                                            name = "기존 자소서 있음",
                                            summary = "DRAFT 상태 자소서가 1개 이상 있는 경우",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                        "career": "2년간 서비스업 근무 경험",
                                        "isNew": false,
                                        "draftResumeList": [
                                            {
                                                "name": "대기업 지원용 자소서",
                                                "createdAt": "2024-01-15"
                                            },
                                            {
                                                "name": "중소기업 지원용 자소서", 
                                                "createdAt": "2024-01-10"
                                            }
                                        ]
                                    }
                                }
                                """
                                    )
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
            )}
    )
    SetResumeResponseDto setResume(@Parameter(hidden = true) @CurrentUser User user);


    @Operation(
            summary = "자소서 질문 목록 조회 API",
            description = "특정 자소서의 질문 목록을 조회하는 API입니다." +
                    "제목이 null일 경우 제목을 입력해주세요. 를 띄워주세요"
            
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질문 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                        {
                            "errorCode": null,
                            "message": "OK",
                            "result": {
                                "resumeQuestionList": [
                                    {
                                        "resumeQuestionId": 1,
                                        "questionTitle": "지원 동기를 작성해주세요",
                                        "createdAt": "2024-01-15",
                                        "updatedAt": "2024-01-15"
                                    },
                                    {
                                        "resumeQuestionId": 2,
                                        "questionTitle": "본인의 강점을 설명해주세요",
                                        "createdAt": "2024-01-15",
                                        "updatedAt": "2024-01-16"
                                    }
                                ]
                            }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "완료된 자소서",
                                    value = """
                        {
                            "status" : 400,
                            "errorCode" : "RESUME_002",
                            "message" : "이미 작성 완료된 자소서입니다."
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
            ),
            @ApiResponse(responseCode = "404", description = "자소서를 찾을 수 없는 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "자소서 없음",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "RESUME_001",
                            "message" : "자소서를 찾을 수 없습니다."
                        }
                        """
                            )
                    )
            )
    })
    GetResumeQuestionListResponseDto getResumeQuestionList(
            @Parameter(hidden = true) @CurrentUser User user, 
            @Parameter(description = "조회할 자소서 ID", example = "1") @PathVariable Long resumeId
    );

    @Operation(
            summary = "자소서 질문 추가 API",
            description = """
                기존 자소서에 새로운 질문을 추가하는 API입니다.
                
                ## 동작 방식:
                - 내용, 워드 제한 등은 별도 수정 API로 업데이트
                
                ## 주의사항:
                - DRAFT 상태의 자소서에만 질문 추가 가능
                - 본인이 작성한 자소서에만 접근 가능
                """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질문 추가 성공",
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
                                    @ExampleObject(
                                            name = "완료된 자소서",
                                            summary = "이미 작성 완료된 자소서",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "RESUME_002",
                                "message" : "이미 작성 완료된 자소서입니다."
                            }
                            """
                                    )
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
            @ApiResponse(responseCode = "404", description = "자소서를 찾을 수 없는 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "자소서 없음",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "RESUME_001",
                            "message" : "자소서를 찾을 수 없습니다."
                        }
                        """
                            )
                    )
            )
    })
    void createResumeQuestion(
            @Parameter(hidden = true) @CurrentUser User user,
            @Parameter(description = "자소서 ID", example = "1") @PathVariable Long resumeId,
            @Valid @RequestBody CreateResumeQuestionRequestDto request
    );

    @Operation(
            summary = "DRAFT 자소서 삭제 API",
            description = """
                DRAFT 상태의 자소서를 삭제하는 API입니다.
                
                ## 삭제 조건:
                - DRAFT 상태의 자소서만 삭제 가능
                - 본인이 작성한 자소서만 삭제 가능
                - COMPLETED 상태의 자소서는 삭제 불가
                
                ## 연관 데이터:
                - 자소서와 함께 모든 질문(ResumeQuestion)도 함께 삭제됩니다 (Cascade)
                """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자소서 삭제 성공",
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
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "완료된 자소서 삭제 시도",
                                    value = """
                        {
                            "status" : 400,
                            "errorCode" : "RESUME_002",
                            "message" : "이미 작성 완료된 자소서입니다."
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
            ),
            @ApiResponse(responseCode = "404", description = "자소서를 찾을 수 없는 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "자소서 없음",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "RESUME_001",
                            "message" : "자소서를 찾을 수 없습니다."
                        }
                        """
                            )
                    )
            )
    })
    void deleteDraftResume(
            @Parameter(hidden = true) @CurrentUser User user, 
            @Parameter(description = "삭제할 자소서 ID", example = "1") @PathVariable Long resumeId
    );
}