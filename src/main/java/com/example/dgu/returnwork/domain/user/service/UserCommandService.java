package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.global.email.service.EmailService;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final EmailService emailService;



    @Transactional
    public void sendEmail(String email){
        emailService.sendEmailAuthentication(email);
    }

    @Transactional
    public void deleteUser(User user){
        if(user.getStatus().equals(Status.DELETED)){
            throw BaseException.type(UserErrorCode.ALREADY_DELETED_USER);
        }
        user.softDelete();
    }

}
