package com.example.dgu.returnwork.domain.auth.controller;

import com.example.dgu.returnwork.domain.auth.dto.request.*;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.ReissueATKResponseDto;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthApi {

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
                                            name = "유효하지 않은 생년월일",
                                            summary = "나이 제한 오류",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "USER_001",
                                "message" : "나이는 14세 이상 100세 이하여야 합니다"
                            }
                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "리소스 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "등록되지 않은 지역",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "REGION_001",
                            "message" : "지역을 찾을 수 없습니다"
                        }
                        """
                            )
                    )
            )
    })
    void signUp(@RequestBody @Valid SignUpRequestDto request);

    @Operation(
            summary = "로그인",
            description = "로그인 API 입니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                        {
                            "errorCode": null,
                            "message": "OK",
                            "result": {
                                "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHprZGx0aEBnbWFpbC5jb20iLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc1MTgyMjU0MywiZXhwIjoxNzUxODM2OTQzfQ.XAepbEuMZ2HzJo9koZTxCNSHmO7fYztB1AprtVMma3Y",
                                "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHprZGx0aEBnbWFpbC5jb20iLCJyb2xlIjoiUkVGUkVTSCIsImlhdCI6MTc1MTgyMjU0MywiZXhwIjoxNzUyNDI3MzQzfQ.aniLKmxj0spkyXDa5Yh7OkESELZAJrCDa0qzBciOkEY"
                            }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "패스워드가 일치하지 않는 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "패스워드 불일치",
                                    value = """
                        {
                            "status" : 400,
                            "errorCode" : "USER_002",
                            "message" : "패스워드가 일치하지 않습니다."
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일인 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "사용자 없음",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "USER_004",
                            "message" : "존재하지 않는 사용자입니다."
                        }
                        """
                            )
                    )
            )
    })
    LoginUserResponseDto login(@RequestBody @Valid LoginUserRequestDto request);

    @Operation(
            summary = "Google 로그인",
            description = "Google OAuth 로그인 API 입니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Google 로그인 성공 또는 회원가입 필요",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "로그인 성공",
                                            summary = "기존 사용자 로그인 성공",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                        "status": "SUCCESS",
                                        "message": "로그인 성공",
                                        "tempToken": null,
                                        "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                                        "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
                                    }
                                }
                                """
                                    ),
                                    @ExampleObject(
                                            name = "회원가입 필요",
                                            summary = "신규 사용자 또는 추가 정보 입력 필요",
                                            value = """
                                {
                                    "errorCode": null,
                                    "message": "OK",
                                    "result": {
                                        "status": "SIGNUP_NEEDED",
                                        "message": "추가 정보를 입력해주세요",
                                        "tempToken": "eyJ...",
                                        "accessToken": null,
                                        "refreshToken": null
                                    }
                                }
                                """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Google OAuth 인증 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "OAuth 인증 실패",
                                    value = """
                        {
                            "status" : 400,
                            "errorCode" : "AUTH_006",
                            "message" : "Google 사용자 정보 조회 실패"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "구글 토큰 오류, 사용자 정보 찾기 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "사용자 정보 찾기 실패",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "AUTH_005",
                            "message" : "사용자 정보를 찾을 수 없습니다."
                        }
                        """
                            )
                    )
            )
    })
    GoogleLoginResponseDto googleLogin(@RequestBody @Valid GoogleLoginRequestDto request);


    @Operation(
            summary = "구글 로그인시 회원가입 API",
            description = "구글 로그인시 회원가입 API"
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
                          "result" : {
                            "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                            "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
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
                                    @ExampleObject(
                                            name = "유효하지 않은 생년월일",
                                            summary = "나이 제한 오류",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "USER_001",
                                "message" : "나이는 14세 이상 100세 이하여야 합니다"
                            }
                            """
                                    ),
                                    @ExampleObject(
                                            name = "이미 회원가입이 완료된 유저",
                                            summary = "이미 회원가입이 완료된 유저",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "AUTH_007",
                                "message" : "이미 회원가입이 완료된 사용자입니다."
                            }
                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자, 토큰 만료 or 토큰 잘못되었을때",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "인증되지 않은 사용자",
                                    value = """
                        {
                            "status" : 401,
                            "errorCode" : "AUTH_001",
                            "message" : "유효하지 않은 토큰입니다."
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "리소스 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "등록되지 않은 지역",
                                    value = """
                        {
                            "status" : 404,
                            "errorCode" : "REGION_001",
                            "message" : "지역을 찾을 수 없습니다"
                        }
                        """
                            )
                    )
            )
    })
    LoginUserResponseDto googleSignup(
            @RequestBody @Valid GoogleSignUpRequestDto request,
            @Parameter(hidden = true) @CurrentUser User user);



    @Operation(
            summary = "액세스 토큰 재요청 API",
            description = "액세스 토큰 만료시, 액세스 토큰 재요청 API 입니다"
    )
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
                            "result": {
                                "accessToken": "eyJhbGciOiJIUzI1NiJ9..."
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
            )
    })
    ReissueATKResponseDto  reissueATK(
            @RequestBody @Valid ReissueATKRequestDto request
            );
}
