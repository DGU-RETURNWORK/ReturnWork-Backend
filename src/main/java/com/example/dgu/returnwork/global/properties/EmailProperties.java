package com.example.dgu.returnwork.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("spring.email")
public class EmailProperties {

    private final String host;
    private final String port;
    private final String username;
    private final String password;

    public EmailProperties(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

}
