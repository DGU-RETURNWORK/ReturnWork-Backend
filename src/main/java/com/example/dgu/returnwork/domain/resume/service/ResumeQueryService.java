package com.example.dgu.returnwork.domain.resume.service;

import com.example.dgu.returnwork.domain.resume.dto.response.DraftResume;
import com.example.dgu.returnwork.domain.resume.dto.response.GetResumeQuestionListResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.SetResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.enums.ResumeStatus;
import com.example.dgu.returnwork.domain.resume.exception.ResumeErrorCode;
import com.example.dgu.returnwork.domain.resume.repository.ResumeRepository;
import com.example.dgu.returnwork.domain.resume.validator.ResumeValidator;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeQueryService {

    private final ResumeRepository resumeRepository;
    private final UserQueryService userQueryService;
    private final ResumeValidator resumeValidator;

    @Transactional(readOnly = true)
    public GetResumeQuestionListResponseDto getResumeQuestionList(User user, Long resumeId) {
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, user.getId())
                .orElseThrow(() -> BaseException.type(ResumeErrorCode.NOT_FOUND_RESUME));

        resumeValidator.validateDraftStatus(resume.getResumeStatus());

        return GetResumeQuestionListResponseDto.from(resume.getResumeQuestions());
    }

    @Transactional(readOnly = true)
    public SetResumeResponseDto setResume(User user){
        String career = userQueryService.getUserCareer(user);
        List<Resume> resumeList = resumeRepository
                .findByUserIdAndResumeStatus(user.getId(), ResumeStatus.DRAFT);

        if(resumeList.isEmpty()){
            return SetResumeResponseDto.of(career, true, null);
        }

        List<DraftResume> draftResumeList = resumeList
                .stream()
                .map(DraftResume::from)
                .toList();

        return SetResumeResponseDto.of(career, false, draftResumeList);
    }




}
