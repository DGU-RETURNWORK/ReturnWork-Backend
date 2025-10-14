package com.example.dgu.returnwork.domain.resume.service;

import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.job.service.JobQueryService;
import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeQuestionRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.entity.ResumeQuestion;
import com.example.dgu.returnwork.domain.resume.exception.ResumeErrorCode;
import com.example.dgu.returnwork.domain.resume.repository.ResumeQuestionRepository;
import com.example.dgu.returnwork.domain.resume.repository.ResumeRepository;
import com.example.dgu.returnwork.domain.resume.validator.ResumeValidator;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeCommandService {

    private final JobQueryService jobQueryService;
    private final ResumeRepository resumeRepository;
    private final ResumeValidator resumeValidator;
    private final ResumeQuestionRepository resumeQuestionRepository;

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

    @Transactional
    public void createResumeQuestion(User user, Long resumeId, CreateResumeQuestionRequestDto request) {
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, user.getId())
                .orElseThrow(() -> BaseException.type(ResumeErrorCode.NOT_FOUND_RESUME));

        resumeValidator.validateDraftStatus(resume.getResumeStatus());

        ResumeQuestion resumeQuestion = ResumeQuestion.create(resume, request.title());

        resume.createResumeQuestion(resumeQuestion);
    }

    @Transactional
    public void deleteDraftResume(User user, Long resumeId){
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, user.getId())
                .orElseThrow(() -> BaseException.type(ResumeErrorCode.NOT_FOUND_RESUME));

        resumeValidator.validateDraftStatus(resume.getResumeStatus());

        resumeRepository.delete(resume);
    }

    private void addResumeQuestions(int count, Resume resume){

        for (int i = 1; i <=count; i++) {
            ResumeQuestion resumeQuestion = ResumeQuestion.create(resume, null);
            resume.createResumeQuestion(resumeQuestion);
        }
    }
}
