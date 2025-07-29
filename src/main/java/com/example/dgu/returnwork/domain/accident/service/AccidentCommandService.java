package com.example.dgu.returnwork.domain.accident.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.dto.request.CreateAccidentRequestDto;
import com.example.dgu.returnwork.domain.accident.repository.AccidentRepository;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccidentCommandService {

    private final UserRepository userRepository;
    private final AccidentRepository accidentRepository;

    public void createAccident(User user, CreateAccidentRequestDto request) {

        Accident accident = Accident.builder()
                .user(user)
                .industryType(request.industryType())
                .accidentType(request.accidentType())
                .injuryArea(request.injuryArea())
                .injurySeverity(request.injurySeverity())
                .detail(request.detail())
                .build();

        accidentRepository.save(accident);
    }
}
