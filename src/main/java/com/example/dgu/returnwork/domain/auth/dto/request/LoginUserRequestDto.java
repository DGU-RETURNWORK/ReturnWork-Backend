package com.example.dgu.returnwork.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequestDto(

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수입니다")
        @Schema(example = "dhzktldh@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다")
        @Schema(example = "ttkdd124@")
        String password

){
}
