package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController implements UserApi {

    private final UserCommandService userCommandService;

    @Override
    @PostMapping("signup")
    public void signUp(SignUpRequestDto request) {
        userCommandService.signUp(request);
    }
}
