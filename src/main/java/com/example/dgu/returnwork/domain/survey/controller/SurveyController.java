package com.example.dgu.returnwork.domain.survey.controller;

import com.example.dgu.returnwork.domain.survey.dto.request.SaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.dto.request.TempSaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.dto.response.GetSurveyResponseDto;
import com.example.dgu.returnwork.domain.survey.service.SurveyCommandService;
import com.example.dgu.returnwork.domain.survey.service.SurveyQueryService;
import com.example.dgu.returnwork.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/possibility/survey")
@RequiredArgsConstructor
public class SurveyController implements SurveyApi {

    private final SurveyQueryService surveyQueryService;
    private final SurveyCommandService surveyCommandService;

    @Override
    @GetMapping("/")
    public GetSurveyResponseDto getSurvey(User user) {
        return surveyQueryService.getSurvey(user.getId());
    }

    @Override
    @PostMapping("/")
    public void saveSurvey(SaveSurveyRequestDto request, User user) {
        surveyCommandService.saveSurvey(request, user);
    }

    @Override
    @PostMapping("/temp")
    public void tempSaveSurvey(TempSaveSurveyRequestDto request, boolean isUpdate, User user) {
        surveyCommandService.tempSaveSurvey(request, isUpdate, user);
    }
}
