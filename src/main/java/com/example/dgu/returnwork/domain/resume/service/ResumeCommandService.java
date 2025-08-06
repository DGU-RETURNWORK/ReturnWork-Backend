package com.example.dgu.returnwork.domain.resume.service;

import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.job.service.JobQueryService;
import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.entity.ResumeQuestion;
import com.example.dgu.returnwork.domain.resume.repository.ResumeRepository;
import com.example.dgu.returnwork.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeCommandService {

    private final JobQueryService jobQueryService;
    private final ResumeRepository resumeRepository;

    @Transactional
    public CreateResumeResponseDto createResume(CreateResumeRequestDto request, User user) {

        Job job = jobQueryService.findJobByName(request.jobName());

        Resume resume = Resume.create(request.title(),
                request.capability(),
                request.career(),
                request.questionCount(),
                user,
                job);


        addResumeQuestions(request.questionCount(), resume);

        Resume savedResume = resumeRepository.save(resume);

        return CreateResumeResponseDto.from(savedResume);
    }

    private void addResumeQuestions(int count, Resume resume){

        for (int i = 1; i <=count; i++) {
            ResumeQuestion resumeQuestion = ResumeQuestion.create(i,resume);
            resume.createResumeQuestion(resumeQuestion);
        }
    }
}
