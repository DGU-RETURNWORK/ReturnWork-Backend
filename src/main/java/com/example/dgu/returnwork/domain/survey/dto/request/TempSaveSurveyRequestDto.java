package com.example.dgu.returnwork.domain.survey.dto.request;

import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.enums.SurveyStatus;
import com.example.dgu.returnwork.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "설문조사 임시저장 요청", example = """
        {
            "answer1": 2,
            "answer2": null,
            "answer3": 4,
            "answer4": null,
            "answer5": 3,
            "answer6": null,
            "answer7": null,
            "answer8": 1,
            "answer9": null,
            "answer10": 5
        }
        """)
public record TempSaveSurveyRequestDto(

        @Schema(description = "설문 답변 1번 (1-5 또는 null)", example = "2")
        @Min(1) @Max(5)
        Integer answer1,

        @Schema(description = "설문 답변 2번 (1-5 또는 null)", example = "null")
        @Min(1) @Max(5)
        Integer answer2,

        @Schema(description = "설문 답변 3번 (1-5 또는 null)", example = "4")
        @Min(1) @Max(5)
        Integer answer3,

        @Schema(description = "설문 답변 4번 (1-5 또는 null)", example = "null")
        @Min(1) @Max(5)
        Integer answer4,

        @Schema(description = "설문 답변 5번 (1-5 또는 null)", example = "3")
        @Min(1) @Max(5)
        Integer answer5,

        @Schema(description = "설문 답변 6번 (1-5 또는 null)", example = "null")
        @Min(1) @Max(5)
        Integer answer6,

        @Schema(description = "설문 답변 7번 (1-5 또는 null)", example = "null")
        @Min(1) @Max(5)
        Integer answer7,

        @Schema(description = "설문 답변 8번 (1-5 또는 null)", example = "1")
        @Min(1) @Max(5)
        Integer answer8,

        @Schema(description = "설문 답변 9번 (1-5 또는 null)", example = "null")
        @Min(1) @Max(5)
        Integer answer9,

        @Schema(description = "설문 답변 10번 (1-5 또는 null)", example = "5")
        @Min(1) @Max(5)
        Integer answer10
) {
    public Survey toEntity(User user){
        return Survey.builder()
                .a1(this.answer1())
                .a2(this.answer2())
                .a3(this.answer3())
                .a4(this.answer4())
                .a5(this.answer5())
                .a6(this.answer6())
                .a7(this.answer7())
                .a8(this.answer8())
                .a9(this.answer9())
                .a10(this.answer10())
                .status(SurveyStatus.PENDING)
                .user(user)
                .build();
    }
}
