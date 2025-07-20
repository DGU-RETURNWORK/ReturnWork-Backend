package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
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
                            schema = @Schema(implementation = CustomErrorResponse.class),
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
            summary = "메일 전송 API",
            description = "이메일을 입력하면 전송하는 API 입니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 전송 성공",
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
                            schema = @Schema(implementation = CustomErrorResponse.class),
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

    @Operation(
            summary = "이메일 인증",
            description = "이메일 인증 API 입니다., 인증시간 만료는 5분으로 설정했습니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 인증 성공",
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
            @ApiResponse(responseCode = "400", description = "이메일 인증시 요청 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "인증 시간 만료 또는 올바르지 않은 이메일",
                                            summary = "인증 시간 만료 또는 올바르지 않은 이메일",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "USER_006",
                                "message" : "인증번호를 재요청해주세요"
                            }
                            """
                                    ),
                            @ExampleObject(
                                            name = "이메일 인증 코드 불일치",
                                            summary = "이메일 인증 코드 불일치",
                                            value = """
                            {
                                "status" : 400,
                                "errorCode" : "USER_005",
                                "message" : "일치하지 않는 이메일 코드입니다."
                            }
                            """
                                    )
                            }
                    )
            )
    })
    void verifyEmail(@RequestBody @Valid VerifyEmailRequestDto request);
}
