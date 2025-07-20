package com.example.dgu.returnwork.domain.auth.service;

import com.example.dgu.returnwork.domain.auth.dto.request.GoogleLoginRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.GoogleSignUpRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.auth.dto.response.GoogleLoginResponseDto;
import com.example.dgu.returnwork.domain.auth.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.auth.exception.AuthErrorCode;
import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.region.service.RegionQueryService;
import com.example.dgu.returnwork.domain.user.User;
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

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleOAuthClient googleOAuthClient;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RegionQueryService regionQueryService;


    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 14;

    @Transactional
    public void signUp(SignUpRequestDto request){

        if(userRepository.existsByEmail(request.email())){
            throw BaseException.type(UserErrorCode.ALREADY_EXIST_EMAIL);
        }

        LocalDate userBirthday = LocalDate.parse(request.birthday());

        validateBirthday(userBirthday);

        Region userRegion = regionQueryService.findRegionByName(request.region());

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(encodePassword(request.password()))
                .birthday(userBirthday)
                .phoneNumber(request.phoneNumber())
                .region(userRegion)
                .career(request.career())
                .build();

        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public LoginUserResponseDto login(LoginUserRequestDto request) {
        log.info("일반 로그인 시도: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw BaseException.type(UserErrorCode.INVALID_PASSWORD);
        }

        return generateLoginTokens(user);
    }

    @Transactional
    public GoogleLoginResponseDto googleLogin(GoogleLoginRequestDto request) {
        log.info("Google 로그인 시작");

        GoogleUserInfo userInfo = googleOAuthClient.getUserInfo(request.accessToken());
        User user = findOrCreateUser(userInfo);

        return user.getStatus() == Status.PENDING 
                ? GoogleLoginResponseDto.signUpNeeded(generateTempToken(user))
                : generateGoogleLoginTokens(user);
    }

    @Transactional
    public LoginUserResponseDto googleSignup(GoogleSignUpRequestDto request, User user) {

        if(!user.getStatus().equals(Status.PENDING)){
            throw BaseException.type(AuthErrorCode.ALREADY_COMPLETED_SIGNUP);
        }

        LocalDate userBirthday = LocalDate.parse(request.birthday());

        validateBirthday(userBirthday);

        Region userRegion = regionQueryService.findRegionByName(request.region());

       user.update(request.name(), request.phoneNumber(), userBirthday, userRegion, request.career());

       return generateLoginTokens(user);

    }

    private void validateBirthday(LocalDate birthday) {
        Period age = Period.between(birthday, LocalDate.now());

        if (age.getYears() < MIN_AGE || age.getYears() > MAX_AGE) {
            throw BaseException.type(UserErrorCode.INVALID_BIRTHDAY);
        }
    }

    // == password 암호화 == //
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // == google Login 메서드 == /
    private User findOrCreateUser(GoogleUserInfo userInfo) {
        return userRepository.findByProviderId(userInfo.providerId())
                .orElseGet(() -> userRepository.save(userInfo.toUser()));
    }

    private String generateTempToken(User user) {
        Authentication authentication = jwtUtil.createAuthentication(user);
        return jwtUtil.generateTempToken(authentication);
    }

    private GoogleLoginResponseDto generateGoogleLoginTokens(User user) {
        Authentication authentication = jwtUtil.createAuthentication(user);
        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);
        
        return GoogleLoginResponseDto.login(accessToken, refreshToken);
    }

    // == 일반 로그인 메서드 == //
    private LoginUserResponseDto generateLoginTokens(User user) {
        Authentication authentication = jwtUtil.createAuthentication(user);
        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        return LoginUserResponseDto.of(accessToken, refreshToken);
    }
}
