package com.example.dgu.returnwork.domain.resume.dto.response;

import com.example.dgu.returnwork.domain.resume.entity.Resume;

import java.time.LocalDate;

public record DraftResume(
        String name,
        LocalDate createdAt
){
    public static DraftResume from(Resume resume) {
        return new DraftResume(resume.getTitle(), resume.getCreatedAt().toLocalDate());
    }
}
