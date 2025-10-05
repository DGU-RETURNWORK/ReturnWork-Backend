package com.example.dgu.returnwork.domain.user.dto.response;

import com.example.dgu.returnwork.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetUserInfoResponseDto(

        @Schema(description = "사용자 이름", example = "추상윤")
        String name,

        @Schema(description = "이메일", example = "dhzktldh@gmail.com")
        String email,

        @Schema(description = "생년월일", example = "2003-03-03")
        String birthday,

        @Schema(description = "전화번호", example = "010-7689-3141")
        String phoneNumber,

        @Schema(description = "경력 사항", example = "요식업 2년차")
        String career,

        @Schema(description = "거주지", example = "서울시 강서구")
        String region,

        @Schema(description = "이미지 url")
        String imageUrl
) {

    /**
     * Create a GetUserInfoResponseDto populated from the given User and image URL.
     *
     * @param user     the User entity whose fields (name, email, birthday, phoneNumber, career, region) are copied into the DTO
     * @param imageUrl the image URL to assign to the DTO
     * @return a GetUserInfoResponseDto containing the user's information and the provided image URL
     */
    public static GetUserInfoResponseDto of(User user, String imageUrl) {
        return GetUserInfoResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .birthday(user.getBirthday().toString())
                .phoneNumber(user.getPhoneNumber())
                .career(user.getCareer())
                .region(user.getRegion().getFullAddress())
                .imageUrl(imageUrl)
                .build();
    }

}