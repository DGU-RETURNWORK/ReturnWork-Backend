package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.region.service.RegionQueryService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.dto.request.SignUpRequestDto;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final RegionQueryService regionQueryService;
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 14;

    @Transactional
    public void signUp(SignUpRequestDto request){


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



}
