package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.global.email.service.EmailService;
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


}
