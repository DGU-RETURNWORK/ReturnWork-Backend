package com.example.dgu.returnwork.global.auth.security;

import com.example.dgu.returnwork.domain.auth.exception.AuthErrorCode;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.exception.BaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null){
            throw BaseException.type(AuthErrorCode.UNAUTHORIZED_USER);
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }


}
