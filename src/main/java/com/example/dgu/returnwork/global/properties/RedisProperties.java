package com.example.dgu.returnwork.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties("spring.data.redis")
public class RedisProperties {


    private final String host;
    private final int port;           // int 타입으로 수정
    private final Pool pool;
    private final int duration = 300;

    public RedisProperties(String host, int port, Pool pool) {
        this.host = host;
        this.port = port;
        this.pool = pool;
    }

    public record Pool(
            int maxActive,
            long maxWait,     // Duration 대신 long (ms)
            int maxIdle,
            int minIdle
    ) {}
}