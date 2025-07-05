package com.example.dgu.returnwork.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/redis/test")
    public String redisTest(){
        redisTemplate.opsForValue().set("test", "Hello Redis!");
        String value = redisTemplate.opsForValue().get("test");
        return "Redis 연결 성공: " + value;
    }
}
