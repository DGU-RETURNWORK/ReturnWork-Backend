package com.example.dgu.returnwork.domain.resume.dto.response;

import com.example.dgu.returnwork.domain.resume.entity.ResumeQuestion;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record GetResumeQuestionListResponseDto(

        List<ResumeQuestionSummary> resumeQuestionList
) {
    public static GetResumeQuestionListResponseDto from(List<ResumeQuestion> resumeQuestionList) {
        List<ResumeQuestionSummary> summaryList = resumeQuestionList.stream()
                .map(ResumeQuestionSummary::from)
                .toList();
        return new GetResumeQuestionListResponseDto(summaryList);
    }

    @Builder
    public record ResumeQuestionSummary(
            Long resumeQuestionId,
            String questionTitle,
            LocalDate createdAt,
            LocalDate updatedAt,
            int questionOrder
    ){

        public static ResumeQuestionSummary from(ResumeQuestion resumeQuestion) {
            return ResumeQuestionSummary.builder()
                    .resumeQuestionId(resumeQuestion.getId())
                    .questionTitle(resumeQuestion.getQuestionTitle())
                    .createdAt(resumeQuestion.getCreatedAt().toLocalDate())
                    .updatedAt(resumeQuestion.getUpdatedAt().toLocalDate())
                    .build();
        }

    };

}

