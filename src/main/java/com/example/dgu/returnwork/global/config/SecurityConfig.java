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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure and build the application's HTTP security filter chain.
     *
     * Configures CSRF disabled, CORS via corsConfigurationSource(), stateless session management,
     * form login and HTTP Basic disabled, URL-based authorization rules (including public endpoints,
     * role requirements for Google login completion, admin and user routes), custom authentication
     * entry point handling, and registers a JWT authentication filter before the username/password filter.
     *
     * @param http the HttpSecurity to configure
     * @param jwtUtil utility used by the JWT authentication filter to validate and parse tokens
     * @param customAuthenticationEntryPoint handler invoked on authentication failures
     * @return the configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtUtil jwtUtil,
                                                   CustomAuthenticationEntryPoint customAuthenticationEntryPoint)
                                                    throws Exception {

        http
                //1. CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                //2. CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                //3. session 사용하지 않음 (Jwt는 STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //4. form login 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                //5. HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                //6. URL별 권한 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/user/duplicate",
                                "/api/user/send",
                                "/api/user/send/code",
                                "/api/auth/login",
                                "/api/auth/google/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/api/auth/reissue",
                                "/api/regions"
                        ).permitAll()
                        .requestMatchers("/api/auth/google/login/complete").hasRole("TEMP_USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().hasRole("USER")
                )

                //7. 커스텀 인증 예외 처리
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOriginPatterns(List.of(
                "http://52.79.80.199:3000",
                "http://localhost:5173",
                "http://localhost:3000")); // 개발환경용

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        
        // 허용할 헤더
        configuration.setAllowedHeaders(List.of("*"));
        
        // 자격증명 허용 (쿠키, Authorization 헤더 등)
        configuration.setAllowCredentials(true);
        
        // preflight 요청 캐시 시간 (초)
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

  
}