package com.example.dgu.returnwork.domain.accident.service;

import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.accident.exception.AccidentErrorCode;
import com.example.dgu.returnwork.domain.accident.repository.AccidentRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class AccidentQueryService {

    private final AccidentRepository accidentRepository;

    public Accident findAccidentById(Long id){

        return accidentRepository.findById(id)
                .orElseThrow(() -> BaseException.type(AccidentErrorCode.ACCIDENT_NOT_FOUND));
    }
}
