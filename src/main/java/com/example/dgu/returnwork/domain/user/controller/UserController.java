package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
import com.example.dgu.returnwork.domain.user.service.UserCommandService;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;



    @Override
    @GetMapping("/duplicate")
    public void emailDuplicateCheck(String email) {
        userQueryService.emailDuplicateCheck(email);
    }

    @Override
    @PostMapping("/send")
    public void sendEmail(String email) {
        userCommandService.sendEmail(email);
    }

    @Override
    @PostMapping("/send/code")
    public void verifyEmail(VerifyEmailRequestDto request) {
        userQueryService.verifyEmail(request);
    }
}
