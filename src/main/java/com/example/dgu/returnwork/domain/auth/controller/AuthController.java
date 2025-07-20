package com.example.dgu.returnwork.domain.auth.controller;

import com.example.dgu.returnwork.domain.auth.dto.request.GoogleSignUpRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.auth.service.AuthService;
import com.example.dgu.returnwork.domain.auth.dto.request.GoogleLoginRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
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

}
