package com.example.dgu.returnwork.domain.survey.dto.request;

import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.enums.SurveyStatus;
import com.example.dgu.returnwork.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "설문조사 완료 요청", example = """
        {
            "answer1": 3,
            "answer2": 4,
            "answer3": 2,
            "answer4": 5,
            "answer5": 1,
            "answer6": 3,
            "answer7": 4,
            "answer8": 2,
            "answer9": 5,
            "answer10": 3
        }
        """)
public record SaveSurveyRequestDto(

        @Schema(description = "설문 답변 1번", example = "3")
        @NotNull
        @Min(1) @Max(5)
        Integer answer1,

        @Schema(description = "설문 답변 2번", example = "4")
        @NotNull
        @Min(1) @Max(5)
        Integer answer2,

        @Schema(description = "설문 답변 3번", example = "2")
        @NotNull
        @Min(1) @Max(5)
        Integer answer3,

        @Schema(description = "설문 답변 4번", example = "5")
        @NotNull
        @Min(1) @Max(5)
        Integer answer4,

        @Schema(description = "설문 답변 5번", example = "1")
        @NotNull
        @Min(1) @Max(5)
        Integer answer5,

        @Schema(description = "설문 답변 6번", example = "3")
        @NotNull
        @Min(1) @Max(5)
        Integer answer6,

        @Schema(description = "설문 답변 7번", example = "4")
        @NotNull
        @Min(1) @Max(5)
        Integer answer7,

        @Schema(description = "설문 답변 8번", example = "2")
        @NotNull
        @Min(1) @Max(5)
        Integer answer8,

        @Schema(description = "설문 답변 9번", example = "5")
        @NotNull
        @Min(1) @Max(5)
        Integer answer9,

        @Schema(description = "설문 답변 10번", example = "3")
        @NotNull
        @Min(1) @Max(5)
        Integer answer10

        ) {

        public Survey toEntity(User user){
                return Survey.builder()
                        .date(LocalDate.now())
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
                        .status(SurveyStatus.SUCCESS)
                        .user(user)
                        .build();
        }
}
