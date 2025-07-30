package com.example.dgu.returnwork.domain.survey.controller;

import com.example.dgu.returnwork.domain.survey.dto.request.SaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.dto.request.TempSaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.dto.response.GetSurveyResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Survey", description = "설문 관련 API")
public interface SurveyApi {

    @Operation(
            summary = "설문조사 조회 API",
            description = "설문조사 조회 API, 수정중인 설문조사가 있을 시 불러오기, 없다면 새로 생성"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "임시저장 설문조사 있는 경우",
                                            summary = "임시저장 설문조사 있는 경우",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                            "message": "임시저장된 설문조사가 존재합니다.",
                                            "answer1": 1,
                                            "answer2": 2,
                                            "answer3": 1,
                                            "answer4": 1,
                                            "answer5": 2,
                                            "answer6": 0,
                                            "answer7": 0,
                                            "answer8": 0,
                                            "answer9": 0,
                                            "answer10": 0
                                    }
                                }
                                """
                                    ),
                                    @ExampleObject(
                                            name = "임시저장된 설문조사가 없는 경우",
                                            summary = "임시저장된 설문조사 없는 경우",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                        "message": "신규 설문조사입니다.",
                                        "answer1": null,
                                        "answer2": null,
                                        "answer3": null,
                                        "answer4": null,
                                        "answer5": null,
                                        "answer6": null,
                                        "answer7": null,
                                        "answer8": null,
                                        "answer9": null,
                                        "answer10": null
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
            )
    })
    GetSurveyResponseDto getSurvey(
            @Parameter(hidden = true) @CurrentUser User user
    );


    @Operation(
            summary = "설문조사 완료 API",
            description = "설문조사 완료 API"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
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
            )
    })
    void saveSurvey(
            @Valid @RequestBody SaveSurveyRequestDto request,
            @Parameter(hidden = true) @CurrentUser User user
    );


    @Operation(
            summary = "설문조사 임시저장 API",
            description = "설문조사 임시저장 API"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
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
            )
    })
    void tempSaveSurvey(
            @Valid @RequestBody TempSaveSurveyRequestDto request,
            @RequestParam(defaultValue = "false")
            @Parameter(description = "기존 임시저장 데이터 업데이트 여부 (첫 임시저장시 false, 기존 데이터 수정시 true)")
            boolean isUpdate,
            @Parameter(hidden = true) @CurrentUser User user);

}
