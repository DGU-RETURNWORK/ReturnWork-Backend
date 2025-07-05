package com.example.dgu.returnwork.domain.user.controller;

import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.user.service.UserCommandService;
import com.example.dgu.returnwork.domain.user.service.UserQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController implements UserApi {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Override
    @PostMapping("signup")
    public void signUp(@Valid @RequestBody SignUpRequestDto request) {
        userCommandService.signUp(request);
    }

    @Override
    @GetMapping("/duplicate")
    public void emailDuplicateCheck(@RequestParam @Email String email) {
        userQueryService.emailDuplicateCheck(email);
    }
}
