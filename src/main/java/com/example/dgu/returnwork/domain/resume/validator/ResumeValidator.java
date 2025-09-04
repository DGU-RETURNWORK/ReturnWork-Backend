package com.example.dgu.returnwork.domain.resume.validator;

import com.example.dgu.returnwork.domain.resume.enums.ResumeStatus;
import com.example.dgu.returnwork.domain.resume.exception.ResumeErrorCode;
import com.example.dgu.returnwork.global.exception.BaseException;
import org.springframework.stereotype.Component;

@Component
public class ResumeValidator {

    public void validateDraftStatus(ResumeStatus resumeStatus){
        if(!resumeStatus.equals(ResumeStatus.DRAFT)){
            throw BaseException.type(ResumeErrorCode.ALREADY_COMPLETED_RESUME);
        }
    }

}
