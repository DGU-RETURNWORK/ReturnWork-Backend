package com.example.dgu.returnwork.domain.resume.controller;

import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeQuestionRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.request.CreateResumeRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.request.UpdateQuestionOrderRequestDto;
import com.example.dgu.returnwork.domain.resume.dto.response.CreateResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.GetResumeQuestionListResponseDto;
import com.example.dgu.returnwork.domain.resume.dto.response.SetResumeResponseDto;
import com.example.dgu.returnwork.domain.resume.service.ResumeCommandService;
import com.example.dgu.returnwork.domain.resume.service.ResumeQueryService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController implements ResumeApi {

    private final ResumeCommandService resumeCommandService;
    private final ResumeQueryService resumeQueryService;

    @Override
    @PostMapping("")
    public CreateResumeResponseDto createResume(CreateResumeRequestDto request, User user) {
        return resumeCommandService.createResume(request, user);
    }

    @Override
    @GetMapping("/form/defaults")
    public SetResumeResponseDto setResume (@CurrentUser User user) {
        return resumeQueryService.setResume(user);
    }

    @Override
    @GetMapping("/{resumeId}")
    public GetResumeQuestionListResponseDto getResumeQuestionList(@CurrentUser User user, @PathVariable Long resumeId) {
        return resumeQueryService.getResumeQuestionList(user, resumeId);
    }

    @Override
    @PostMapping("/{resumeId}")
    public void createResumeQuestion(User user, Long resumeId, CreateResumeQuestionRequestDto request) {
        resumeCommandService.createResumeQuestion(user, resumeId, request);
    }

    @DeleteMapping("/{resumeId}")
    public void deleteDraftResume(@CurrentUser User user, @PathVariable Long resumeId) {
        resumeCommandService.deleteDraftResume(user, resumeId);
    }

    @PatchMapping("/{resumeId}/{resumeQuestionId}")
    public void updateQuestionOrder(@CurrentUser User user,
                                    @PathVariable Long resumeId,
                                    @PathVariable Long resumeQuestionId,
                                    @RequestBody UpdateQuestionOrderRequestDto request){
        resumeCommandService.updateQuestionOrder(user, resumeId, resumeQuestionId, request);
    }
}
