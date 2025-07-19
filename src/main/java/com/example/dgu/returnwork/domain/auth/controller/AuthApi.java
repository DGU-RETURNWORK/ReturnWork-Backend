package com.example.dgu.returnwork.domain.auth.controller;

import com.example.dgu.returnwork.domain.auth.dto.request.GoogleLoginRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
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

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthApi {

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
            description = "Google OAuth 로그인 API 입니다, 아마 swagger로 테스트가 안될거에요(access token 받아오는거 때문에)"
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
                            "errorCode" : "AUTH_005",
                            "message" : "Google 사용자 정보 조회 실패"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "찾을 수 없는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "찾을 수 없는 사용자",
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
}
