package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

   private final UserRepository userRepository;

   @Transactional(readOnly = true)
   public void emailDuplicateCheck(String email){

       if(userRepository.existsByEmail(email)){
           throw BaseException.type(UserErrorCode.ALREADY_EXIST_EMAIL);
       }

   }

}
