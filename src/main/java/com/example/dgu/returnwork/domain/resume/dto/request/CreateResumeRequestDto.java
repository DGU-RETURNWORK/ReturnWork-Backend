package com.example.dgu.returnwork.domain.resume.dto.request;

import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.enums.ResumeStatus;
import com.example.dgu.returnwork.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResumeRequestDto (

    @NotNull
    @Min(1) @Max(5)
    @Schema(name = "questionCount", example = "3")
    int questionCount,

    @NotBlank
    @Schema(name = "title", example = "대기업 면접 자소서")
    String title,

    @NotBlank
    @Schema(name = "jobName", example = "농업")
    String jobName,

    @NotBlank
    @Schema(name = "capability", example = "요식업")
    String capability,

    @NotBlank
    @Schema(name = "career", example = "요식업 2년 근무 경험")
    String career
    ){

}
