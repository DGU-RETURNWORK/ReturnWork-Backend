package com.example.dgu.returnwork.global.auth.oauth;

import com.example.dgu.returnwork.domain.auth.exception.AuthErrorCode;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GoogleOAuthClient {

    private final WebClient googleWebClient;

    public GoogleUserInfo getUserInfo(String accessToken) {
        try {
            GoogleUserInfo userInfo =  googleWebClient.get()
                                        .uri("/userinfo?access_token={token}", accessToken)
                                        .retrieve()
                                        .bodyToMono(GoogleUserInfo.class)
                                        .block();

            if (userInfo == null) {
                throw new BaseException(AuthErrorCode.GOOGLE_USER_NOT_FOUND);
            }

            return userInfo;

        } catch (Exception e) {
            throw BaseException.type(AuthErrorCode.GOOGLE_OAUTH_FAILED);
        }
    }
}
