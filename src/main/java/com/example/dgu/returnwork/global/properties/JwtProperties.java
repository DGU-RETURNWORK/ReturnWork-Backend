package com.example.dgu.returnwork.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("jwt.token")
public class JwtProperties {

    private final String secretKey;
    private final String issuer;
    private final String header;
    private final String prefix;
    private final Expiration expiration;


    public JwtProperties(String secretKey, String issuer, String header, String prefix, Expiration expiration) {
        this.secretKey = secretKey;
        this.issuer = issuer;
        this.header = header;
        this.prefix = prefix;
        this.expiration = expiration;
    }


    public record Expiration(Long access, Long refresh) {
    }
}
