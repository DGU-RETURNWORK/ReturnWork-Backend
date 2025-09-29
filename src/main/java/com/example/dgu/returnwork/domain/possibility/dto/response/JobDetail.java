package com.example.dgu.returnwork.domain.possibility.dto.response;

import lombok.Builder;

@Builder
public record JobDetail (
        String jobType,
        String jobName,
        String imgUrl,
        int jobFitness,
        String jobCode,
        String description
){
}
