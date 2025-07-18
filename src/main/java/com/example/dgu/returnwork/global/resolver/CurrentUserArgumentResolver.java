package com.example.dgu.returnwork.global.resolver;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.exception.UserErrorCode;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import com.example.dgu.returnwork.global.annotation.CurrentUser;
import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.exception.CommonErrorCode;
import com.example.dgu.returnwork.global.jwt.JwtTokenProvider;
import com.example.dgu.returnwork.global.security.TokenValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) &&
                parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String token = extractTokenFromRequest(webRequest);

        if(token == null){
            CurrentUser annotation = parameter.getParameterAnnotation(CurrentUser.class);
            if(annotation.required()){
                throw BaseException.type(CommonErrorCode.INVALID_TOKEN);
            }
            return null;
        }

        TokenValidationResult validationResult = jwtTokenProvider.validateToken(token);

        if(validationResult == TokenValidationResult.INVALID){
            throw BaseException.type(CommonErrorCode.INVALID_TOKEN);
        }

        if(validationResult == TokenValidationResult.EXPIRED){
            throw BaseException.type(CommonErrorCode.EXPIRED_TOKEN);
        }

        String email = jwtTokenProvider.getEmailFromToken(token);


        return userRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));
    }

    private String extractTokenFromRequest(NativeWebRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
