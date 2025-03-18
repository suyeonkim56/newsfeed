package com.example.neewfeed.common.resolver;

import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        // @Auth 어노테이션과 AuthUser 타입이 함께 사용되지 않은 경우 예외 발생
        if (hasAuthAnnotation != isAuthUserType) {
            throw new IllegalStateException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }

        System.out.println("@Auth 사용 확인");
        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        System.out.println("리졸버 통과");
        // JwtFilter 에서 set 한 userId 값을 가져옴
        Long userId = (Long) request.getAttribute("userId");
        System.out.println("아이디 가져옴");
        AuthUser authUser = new AuthUser(userId);
        System.out.println("authUser.getId : " + authUser.getId());
        return authUser;
    }
}
