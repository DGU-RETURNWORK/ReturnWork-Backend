package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.user.dto.request.VerifyEmailRequestDto;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

   private final UserRepository userRepository;
   private final RedisUtil redisUtil;

   @Transactional(readOnly = true)
   public void emailDuplicateCheck(String email){

       if(userRepository.existsByEmail(email)){
           throw BaseException.type(UserErrorCode.ALREADY_EXIST_EMAIL);
       }

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

}
