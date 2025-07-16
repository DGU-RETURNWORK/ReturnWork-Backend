package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
import com.example.dgu.returnwork.domain.user.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.user.service.UserCommandService;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController implements UserApi {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Override
    @PostMapping("/signup")
    public void signUp(SignUpRequestDto request) {
        userCommandService.signUp(request);
    }

    @Override
    @GetMapping("/duplicate")
    public void emailDuplicateCheck(String email) {
        userQueryService.emailDuplicateCheck(email);
    }

    @Override
    @PostMapping("/login")
    public LoginUserResponseDto loginUser(LoginUserRequestDto request) {
        return userQueryService.loginUser(request);
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
