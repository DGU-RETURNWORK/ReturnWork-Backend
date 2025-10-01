package com.example.dgu.returnwork.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws.s3")
public class S3Properties {
    private final String bucket;
    private final String region;
    private final String accessKey;
    private final String secretKey;

    public String getBaseUrl(){
        return String.format("https://%s.s3.%s.amazonaws.com", bucket, region);
    }
}
