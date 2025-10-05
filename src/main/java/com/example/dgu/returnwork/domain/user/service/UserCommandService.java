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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
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

    /**
     * Updates the given user's profile fields (name, phone number, birthday, region, and career) from the provided request.
     *
     * The request birthday is validated and the region is resolved by its id before the user's data is updated.
     *
     * @param user the user to update
     * @param request DTO containing the new profile values; `birthday` must be an ISO-8601 date string and `regionId` must refer to an existing region
     */
    @Transactional
    public void updateUserInfo(User user, UpdateUserInfoRequestDto request) {

        LocalDate userBirthday = LocalDate.parse(request.birthday());

        userValidator.validateBirthday(userBirthday);

        Region userRegion = regionQueryService.findRegionById(request.regionId());

        user.update(request.name(), request.phoneNumber(), userBirthday, userRegion, request.career());

    }

    @Transactional
    public void updateProfileImage(User user, MultipartFile profileImage) {
        String oldImageKey = user.getImageKey();
        String newKey = null;

        try{
            newKey = s3Util.uploadFile(profileImage, "users/" + user.getId() + "/profiles");

            user.updateProfile(newKey);

            if(oldImageKey != null && !oldImageKey.isEmpty()){
                try{
                    s3Util.deleteFile(oldImageKey);
                }catch(Exception e){
                    log.warn("Failed to delete old profile image: {}", oldImageKey, e);
                }
            }
        }catch(Exception e){
            if(newKey != null){
                try{
                    s3Util.deleteFile(newKey);
                } catch (Exception cleanupException){
                    log.error("Failed to cleanup uploaded file: {}", newKey, cleanupException);
                }
            }
            throw e;
        }
    }

    @Transactional
    public void deleteProfileImage(User user) {
        if(user.getImageKey() != null) {
            s3Util.deleteFile(user.getImageKey());
        }
        user.deleteProfile();
    }
}