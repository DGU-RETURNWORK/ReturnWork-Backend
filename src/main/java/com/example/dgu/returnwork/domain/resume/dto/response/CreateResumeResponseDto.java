package com.example.dgu.returnwork.domain.resume.dto.response;

import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.entity.ResumeQuestion;

import java.util.List;

public record CreateResumeResponseDto(

        Long resumeId,

        int questionCount,

        List<Long> resumeQuestionIds
) {

    public static CreateResumeResponseDto from(Resume resume){
        List<Long> questionResumeIds = resume.getResumeQuestions()
                .stream()
                .map(ResumeQuestion::getId)
                .toList();

        return new  CreateResumeResponseDto(resume.getId(), resume.getQuestionCount(), questionResumeIds);
    }
}
