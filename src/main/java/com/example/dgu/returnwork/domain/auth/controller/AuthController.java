package com.example.dgu.returnwork.domain.auth.controller;

import com.example.dgu.returnwork.domain.auth.dto.request.*;
import com.example.dgu.returnwork.domain.auth.dto.response.ReissueATKResponseDto;
import com.example.dgu.returnwork.domain.auth.service.AuthService;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public void signUp(SignUpRequestDto request) {
        authService.signUp(request);
    }

    @Override
    @PostMapping("/login")
    public LoginUserResponseDto login(@Valid @RequestBody LoginUserRequestDto request) {
        return authService.login(request);
    }

    @Override
    @PostMapping("/google/login")
    public GoogleLoginResponseDto googleLogin(@Valid @RequestBody GoogleLoginRequestDto request) {
        return authService.googleLogin(request);
    }

    @Override
    @PatchMapping("/google/login/complete")
    public LoginUserResponseDto googleSignup(GoogleSignUpRequestDto request, User user) {
        return authService.googleSignup(request, user);
    }

    @Override
    @PostMapping("/reissue")
    public ReissueATKResponseDto reissueATK(ReissueATKRequestDto request) {
        return authService.reissueATK(request);
    }

    @Override
    @PostMapping("/logout")
    public void logout() {
        log.info("사용자 로그아웃 요청");
    }
}
