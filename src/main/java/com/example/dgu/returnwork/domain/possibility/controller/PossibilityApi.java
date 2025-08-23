package com.example.dgu.returnwork.domain.possibility.controller;

import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityResponseDto;
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

@Tag(name = "Possibility", description = "직무복귀 가능성 관련 API")
public interface PossibilityApi {

    @Operation(
            summary = "직무 복귀 가능성 확인 API",
            description = "사고 정보와 설문 기반으로 직무 복귀 가능성에 대한 OpenAI API 응답을 반환하는 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지피티 기반 직무복귀 가능성 응답 완료",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                                            {
                                              "errorCode": null,
                                              "message": "OK",
                                              "result": {
                                                "jobSummaries": [
                                                  {
                                                    "jobName": "기계 조립원",
                                                    "jobFitness": 85,
                                                    "jobCode": "01010101"
                                                  },
                                                  {
                                                    "jobName": "전기 기기 조작원",
                                                    "jobFitness": 75,
                                                    "jobCode": "01010102"
                                                  },
                                                  {
                                                    "jobName": "물류 창고 관리자",
                                                    "jobFitness": 70,
                                                    "jobCode": "01010103"
                                                  }
                                                ],
                                                "capabilities": [
                                                  "세밀한 손작업 필요 직무",
                                                  "고정밀/조립 제작 작업",
                                                  "중량물 취급이 많은 직무"
                                                ]
                                              }
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
                                    ),
                                    @ExampleObject(
                                            name = "요청 형식 오류(OpenAI 쪽 4xx 포함)",
                                            value = """
                                            {
                                              "status": 400,
                                              "errorCode": "OPENAI_007",
                                              "message": "요청 형식이 올바르지 않습니다."
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
                                    ),
                                    @ExampleObject(
                                            name = "OpenAI 인증 실패(프록시로 401 매핑 시)",
                                            value = """
                                            {
                                              "status": 401,
                                              "errorCode": "OPENAI_004",
                                              "message": "OpenAI 인증에 실패했습니다."
                                            }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "403", description = "OpenAI 호출 권한 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "권한 없음",
                                    value = """
                            {
                              "status": 403,
                              "errorCode": "OPENAI_005",
                              "message": "OpenAI 호출 권한이 없습니다."
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                        "status": 404,
                                                        "errorCode": "USER_004",
                                                        "message": "존재하지 않는 사용자입니다."
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "OpenAI 업스트림 404",
                                            value = """
                                                    {
                                                      "status": 404,
                                                      "errorCode": "OPENAI_006",
                                                      "message": "OpenAI 업스트림에서 리소스를 찾지 못했습니다."
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "502", description = "OpenAI 연동 중 게이트웨이 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "호출 실패",
                                            value = """
                                {
                                  "status": 502,
                                  "errorCode": "OPENAI_001",
                                  "message": "OpenAi 호출에 실패하였습니다."
                                }
                                """
                                    ),
                                    @ExampleObject(
                                            name = "빈 응답",
                                            value = """
                                {
                                  "status": 502,
                                  "errorCode": "OPENAI_002",
                                  "message": "OpenAI 응답이 비어 있습니다."
                                }
                                """
                                    ),
                                    @ExampleObject(
                                            name = "응답 파싱 실패",
                                            value = """
                                {
                                  "status": 502,
                                  "errorCode": "OPENAI_008",
                                  "message": "OpenAI 응답 파싱에 실패했습니다."
                                }
                                """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "504", description = "OpenAI 응답 지연(타임아웃)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "타임아웃",
                                    value = """
                            {
                              "status": 504,
                              "errorCode": "OPENAI_003",
                              "message": "OpenAI 응답이 지연되었습니다."
                            }
                            """
                            )
                    )
            )
    })
    @SecurityRequirement(name = "JWT")
    GetPossibilityResponseDto getPossibility(
            @Parameter(hidden = true) @CurrentUser User user,
            @RequestBody @Valid GetPossibilityRequestDto request
    );
}
