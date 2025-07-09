package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.dto.request.LoginUserRequestDto;
import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
import com.example.dgu.returnwork.domain.user.dto.response.LoginUserResponseDto;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.jwt.JwtTokenProvider;
import com.example.dgu.returnwork.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserQueryService {

   private final UserRepository userRepository;
   private final JwtTokenProvider jwtTokenProvider;
   private final PasswordEncoder passwordEncoder;
   private final RedisUtil redisUtil;

   @Transactional(readOnly = true)
   public void emailDuplicateCheck(String email){

       if(userRepository.existsByEmail(email)){
           throw BaseException.type(UserErrorCode.ALREADY_EXIST_EMAIL);
       }

   }

    @Transactional(readOnly = true)
    public LoginUserResponseDto loginUser (LoginUserRequestDto request){

        User currentUser = userRepository.findByEmail(request.email())
                .orElseThrow(()->BaseException.type(UserErrorCode.USER_NOT_FOUND));

        if (!matchPassword(currentUser.getPassword(), request.password())){
            throw BaseException.type(UserErrorCode.INVALID_PASSWORD);
        }


        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(currentUser.getRole().name());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.email(),
                null,
                Collections.singletonList(authority)
                );


        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return LoginUserResponseDto.of(accessToken, refreshToken);
    }


    @Transactional(readOnly = true)
    public void verifyEmail(VerifyEmailRequestDto request){

       String storedCode = redisUtil.getData(request.email());

       if(storedCode == null){
           throw BaseException.type(UserErrorCode.EMAIL_CODE_ERROR);
       }

       if(!storedCode.equals(request.code())){
           throw BaseException.type(UserErrorCode.INVALID_EMAIL_CODE);
       }


    }

    // == password 확인 == //
    private boolean matchPassword(String userPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, userPassword);
    }

}
