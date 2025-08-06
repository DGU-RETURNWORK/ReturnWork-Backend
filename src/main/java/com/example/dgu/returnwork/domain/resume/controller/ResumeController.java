package com.example.dgu.returnwork.domain.resume.controller;

import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.SetResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.service.ResumeCommandService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import com.example.dgu.returnwork.global.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController implements ResumeApi {

    private final ResumeCommandService resumeCommandService;
    private final UserQueryService userQueryService;

    @Override
    @PostMapping("/")
    public CreateResumeResponseDto CreateResume(CreateResumeRequestDto request, User user) {
        return resumeCommandService.createResume(request, user);
    }

    @Override
    @GetMapping("/form/defaults")
    public SetResumeResponseDto setResume (@CurrentUser User user) {
        return userQueryService.getUserCareer(user);
    }

}
