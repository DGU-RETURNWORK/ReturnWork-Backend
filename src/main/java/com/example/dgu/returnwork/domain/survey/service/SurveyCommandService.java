package com.example.dgu.returnwork.domain.survey.service;

import com.example.dgu.returnwork.domain.survey.dto.request.SaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.dto.request.TempSaveSurveyRequestDto;
import com.example.dgu.returnwork.domain.survey.enums.SurveyStatus;
import com.example.dgu.returnwork.domain.survey.repository.SurveyRepository;
import com.example.dgu.returnwork.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyCommandService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public void saveSurvey(SaveSurveyRequestDto request, User user){

        // 기존 수정중인 survey 삭제
        surveyRepository.deleteByUserIdAndStatus(user.getId(), SurveyStatus.PENDING);

        surveyRepository.save(request.toEntity(user));

    }

    @Transactional
    public void tempSaveSurvey(TempSaveSurveyRequestDto request, boolean isUpdate, User user){

        if(isUpdate){
            surveyRepository.deleteByUserIdAndStatus(user.getId(), SurveyStatus.PENDING);
        }

        surveyRepository.save(request.toEntity(user));
    }
}
