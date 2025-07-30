package com.example.dgu.returnwork.domain.survey.service;

import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.dto.response.GetSurveyResponseDto;
import com.example.dgu.returnwork.domain.survey.enums.SurveyStatus;
import com.example.dgu.returnwork.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyQueryService {

    private final SurveyRepository surveyRepository;


    @Transactional(readOnly = true)
    public GetSurveyResponseDto getSurvey(UUID userId){

            Optional<Survey> survey = surveyRepository.findByUserIdAndStatus(userId, SurveyStatus.PENDING);

            if(survey.isPresent()){
                return GetSurveyResponseDto.newSurvey(survey.get());
            }

            else return GetSurveyResponseDto.notFoundSurvey();

    }
}
