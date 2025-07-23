package com.example.dgu.returnwork.global.config;

import com.example.dgu.returnwork.global.auth.jwt.JwtAuthenticationFilter;
import com.example.dgu.returnwork.global.auth.jwt.JwtUtil;
import com.example.dgu.returnwork.global.auth.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtUtil jwtUtil,
                                                   CustomAuthenticationEntryPoint customAuthenticationEntryPoint)
                                                    throws Exception {

        http
                //1. CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                //2. session 사용하지 않음 (Jwt는 STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //3. form login 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                //4. HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                //5. URL별 권한 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/duplicate",
                                "/api/auth/send",
                                "/api/auth/send/code",
                                "/api/auth/login",
                                "/api/auth/google/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/api/auth/reissue"
                        ).permitAll()
                        .requestMatchers("/api/auth/google/login/complete").hasRole("TEMP_USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().hasRole("USER")
                )

                //6. 커스텀 인증 예외 처리
                .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
