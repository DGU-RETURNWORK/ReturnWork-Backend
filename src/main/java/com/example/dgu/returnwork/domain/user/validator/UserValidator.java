package com.example.dgu.returnwork.domain.user.validator;

import com.example.dgu.returnwork.domain.auth.exception.AuthErrorCode;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 14;

    public void validateBirthday(LocalDate birthday) {
        Period age = Period.between(birthday, LocalDate.now());

        if (age.getYears() < MIN_AGE || age.getYears() > MAX_AGE) {
            throw BaseException.type(UserErrorCode.INVALID_BIRTHDAY);
        }
    }

    public void matchPassword(String password, String userPassword) {
            if(!passwordEncoder.matches(password, userPassword)){
                throw BaseException.type(UserErrorCode.INVALID_PASSWORD);
            }

    }

    public void existEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw BaseException.type(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
    }

    public void checkPendingUser(Status status){
        if(!status.equals(Status.PENDING)){
            throw BaseException.type(AuthErrorCode.ALREADY_COMPLETED_SIGNUP);
        }

    }


}
