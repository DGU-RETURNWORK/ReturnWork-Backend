package com.example.dgu.returnwork.domain.auth.service;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.auth.dto.request.GoogleLoginRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.auth.jwt.JwtUtil;
import com.example.dgu.returnwork.global.auth.oauth.GoogleOAuthClient;
import com.example.dgu.returnwork.global.auth.oauth.GoogleUserInfo;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleOAuthClient googleOAuthClient;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginUserResponseDto login(LoginUserRequestDto request) {
        log.info("일반 로그인 시도: {}", request.email());

        User currentUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), currentUser.getPassword())) {
            throw BaseException.type(UserErrorCode.INVALID_PASSWORD);
        }

        Authentication authentication = jwtUtil.createAuthentication(currentUser);
        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        return LoginUserResponseDto.of(accessToken, refreshToken);
    }

    public GoogleLoginResponseDto googleLogin(GoogleLoginRequestDto request) {
        log.info("Google 로그인 시작");

        GoogleUserInfo userInfo = googleOAuthClient.getUserInfo(request.accessToken());

        Optional<User> user = userRepository.findByProviderId(userInfo.providerId());

        if (user.isEmpty() || user.get().getStatus().equals(Status.PENDING)) {
            return GoogleLoginResponseDto.signUpNeeded();
        }
        User currentUser = user.get();

        Authentication authentication = jwtUtil.createAuthentication(currentUser);
        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        return GoogleLoginResponseDto.login(accessToken, refreshToken);
    }
}
