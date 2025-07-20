package com.example.dgu.returnwork.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GoogleSignUpRequestDto (

        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 2, max = 15, message = "이름은 2~15자 내외로 입력해주세요")
        @Pattern(regexp = "^[가-힣]+$", message= "사용자 이름은 한글만 입력해주세요")
        @Schema(example = "추상윤")
        String name,


        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일은 YYYY-MM-DD 형식으로 입력해주세요")
        @Schema(example = "2003-03-03")
        String birthday,

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "휴대폰 번호는 010-0000-0000 형식으로 입력해주세요")
        @Schema(example = "010-7689-3141")
        String phoneNumber,

        @Schema(example = "서울시 강서구")
        String region,

        @Size(max = 300, message = "경력사항은 300자 이내로 입력해주세요")
        @Schema(example = "1년간 요식업 근무 경험")
        String career

){
}
