package com.example.dgu.returnwork.global.jwt;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.exception.CommonErrorCode;
import com.example.dgu.returnwork.global.properties.JwtProperties;
import com.example.dgu.returnwork.global.security.CustomUserDetails;
import com.example.dgu.returnwork.global.security.TokenValidationResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider{


    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;


    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }


    public String generateAccessToken(Authentication authentication){
        String email = authentication.getName();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpiration().access());


        return Jwts.builder()
                .setSubject(email)
                .claim("role", authentication.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(Authentication authentication){
        String email = authentication.getName();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpiration().refresh());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", "REFRESH")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public TokenValidationResult validateToken(String token){

        if(token == null){
            return TokenValidationResult.INVALID;
        }

        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return TokenValidationResult.VALID;
        } catch (ExpiredJwtException e){
            return TokenValidationResult.EXPIRED;
        }catch(JwtException | IllegalArgumentException e ){
            return TokenValidationResult.INVALID;
        }

    }


    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.type(CommonErrorCode.INVALID_TOKEN));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getPrefix())){
            return bearerToken.substring(jwtProperties.getPrefix().length());
        }
        return null;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }



}
