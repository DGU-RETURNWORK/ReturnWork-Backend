package com.example.dgu.returnwork.domain.user.service;

import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.region.service.RegionQueryService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.dto.request.UpdateUserInfoRequestDto;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.validator.UserValidator;
import com.example.dgu.returnwork.global.email.service.EmailService;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final EmailService emailService;
    private final RegionQueryService regionQueryService;
    private final UserValidator userValidator;
    private final S3Util s3Util;


    @Transactional
    public void sendEmail(String email){
        emailService.sendEmailAuthentication(email);
    }

    @Transactional
    public void deleteUser(User user){
        if(user.getStatus().equals(Status.DELETED)){
            throw BaseException.type(UserErrorCode.ALREADY_DELETED_USER);
        }
        user.softDelete();
    }

    @Transactional
    public void updateUserInfo(User user, UpdateUserInfoRequestDto request) {

        LocalDate userBirthday = LocalDate.parse(request.birthday());

        userValidator.validateBirthday(userBirthday);

        Region userRegion = regionQueryService.findRegionByName(request.region());

        user.update(request.name(), request.phoneNumber(), userBirthday, userRegion, request.career());

    }

    @Transactional
    public void updateProfileImage(User user, MultipartFile profileImage) {
        if(user.getImageKey() != null && !user.getImageKey().isEmpty()){
            s3Util.deleteFile(user.getImageKey());
        }

        String key = s3Util.uploadFile(profileImage, "users/" + user.getId() + "/profiles");

        user.updateProfile(key);
    }

    @Transactional
    public void deleteProfileImage(User user) {
        if(user.getImageKey() != null) {
            s3Util.deleteFile(user.getImageKey());
        }
        user.deleteProfile();
    }
}
