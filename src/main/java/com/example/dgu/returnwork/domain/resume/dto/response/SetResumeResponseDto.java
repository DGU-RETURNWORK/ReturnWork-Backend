package com.example.dgu.returnwork.domain.resume.dto.response;

import java.util.List;

public record SetResumeResponseDto(

        String career,

        boolean isNew,

        List<DraftResume> draftResumeList

) {


    public static SetResumeResponseDto of(String career, boolean isNew, List<DraftResume> draftResumeList) {
        return new SetResumeResponseDto(career, isNew, draftResumeList);
    };
}
