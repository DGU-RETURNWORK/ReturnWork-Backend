package com.example.dgu.returnwork.global.auth.oauth;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.enums.Provider;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfo (

        @JsonProperty("sub")
        String providerId,

        String name,
        String email,
        String picture
){
    public User toUser(){
        return User.builder()
                .email(this.email)
                .provider(Provider.GOOGLE)
                .providerId(this.providerId)
                .status(Status.PENDING)
                .build();
    }
}
