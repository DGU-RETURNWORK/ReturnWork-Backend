package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.dto.request.UpdateUserInfoRequestDto;
import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
import com.example.dgu.returnwork.domain.user.dto.response.GetUserInfoResponseDto;
import com.example.dgu.returnwork.domain.user.service.UserCommandService;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
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

    @Override
    @PatchMapping("/delete")
    public void deleteUser(User user) {
        userCommandService.deleteUser(user);
    }

    @Override
    @PatchMapping("")
    public void updateUserInfo(User user, UpdateUserInfoRequestDto request) {
        userCommandService.updateUserInfo(user, request);
    }

    @Override
    @GetMapping("")
    public GetUserInfoResponseDto getUserInfo(User user) {
        return userQueryService.getUserInfo(user);
    }
}
