package com.example.dgu.returnwork.domain.job.service;

import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.job.exception.JobErrorCode;
import com.example.dgu.returnwork.domain.job.repository.JobRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class JobQueryService {

    private final JobRepository jobRepository;

    public Job findJobByName(String name){

        return jobRepository.findJobByName(name)
                .orElseThrow(() -> BaseException.type(JobErrorCode.JOB_NOT_FOUND));
    }

}
