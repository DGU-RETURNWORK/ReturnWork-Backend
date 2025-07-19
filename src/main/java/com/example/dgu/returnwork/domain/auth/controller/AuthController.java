package com.example.dgu.returnwork.domain.auth.controller;

import com.example.dgu.returnwork.domain.auth.service.AuthService;
import com.example.dgu.returnwork.domain.auth.dto.request.GoogleLoginRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public LoginUserResponseDto login(@Valid @RequestBody LoginUserRequestDto request) {
        return authService.login(request);
    }

    @Override
    @PostMapping("/login/google")
    public GoogleLoginResponseDto googleLogin(@Valid @RequestBody GoogleLoginRequestDto request) {
        return authService.googleLogin(request);
    }
}
