package com.example.dgu.returnwork.global.util;

import com.example.dgu.returnwork.global.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

  @RequiredArgsConstructor
    @Service
    public class RedisUtil {

        private final StringRedisTemplate template;
        private final RedisProperties properties;

        public String getData(String key) {
            ValueOperations<String, String> valueOperations= template.opsForValue();
            return valueOperations.get(key);
        }

        public boolean existData(String key) {
            return Boolean.TRUE.equals(template.hasKey(key));
        }

        public void setDataExpire(String key, String value){
            ValueOperations<String, String> valueOperations = template.opsForValue();
            Duration expireDuration = Duration.ofSeconds(properties.getDuration());
            valueOperations.set(key, value, expireDuration);
        }

        public void deleteData(String key){
            template.delete(key);
        }

        public void createRedisData(String toEmail, String code){
            if(existData(toEmail)){
                deleteData(toEmail);
            }

            setDataExpire(toEmail, code);
        }

        public String createCertifyNum(){
            String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < 6; i++){
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        }
}

