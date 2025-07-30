package com.example.dgu.returnwork.domain.survey.repository;


import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.enums.SurveyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByUserIdAndStatus(UUID userId, SurveyStatus status);

    void deleteByUserIdAndStatus(UUID userId, SurveyStatus status);

}
