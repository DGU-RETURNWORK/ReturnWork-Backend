package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.user.dto.response.LoginUserResponseDto;
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
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
                            schema = @Schema(implementation = CustomErrorResponse.class), // 통일
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
            summary = "이메일 중복 체크",
            description = "이메일 중복 체크 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 중복 X",
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
            @ApiResponse(responseCode = "400", description = "이미 가입한 아이디",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class), // 통일
                            examples = @ExampleObject(
                                    name = "중복",
                                    value = """
                        {
                            "status" : 400,
                            "errorCode" : "USER_003",
                            "message" : "이미 가입된 이메일입니다."
                        }
                        """
                            )
                    )
            )
    })
    void emailDuplicateCheck(
            @Parameter(description = "확인할 이메일 주소", example = "dhzktldh@gmail.com")
            @RequestParam @Email String email);



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
                            schema = @Schema(implementation = CustomErrorResponse.class), // 통일
                            examples = @ExampleObject(
                                    name = "중복",
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
                            schema = @Schema(implementation = CustomErrorResponse.class), // 통일
                            examples = @ExampleObject(
                                    name = "중복",
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
    LoginUserResponseDto loginUser(@RequestBody @Valid LoginUserRequestDto request);

    @Operation(
            summary = "메일 전송 API",
            description = "이메일을 입력하면 전송하는 API 입니다"
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
                           }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "이메일이 발송되지 않는 경우",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class), // 통일
                            examples = @ExampleObject(
                                    name = "이메일 전송 오류",
                                    value = """
                        {
                            "status" : 500,
                            "errorCode" : "Email_001",
                            "message" : "이메일 발송에 실패했습니다."
                        }
                        """
                            )
                    )
            )
    })
    void sendEmail(
            @Parameter(description = "이메일을 받을 주소", example = "dhzktldh@gmail.com")
            @RequestParam @Email String email);
}