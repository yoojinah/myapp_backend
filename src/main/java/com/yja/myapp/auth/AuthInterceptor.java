package com.yja.myapp.auth;

import com.yja.myapp.auth.utill.JwUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    // 토큰 검증 및 subject. claim (토큰 내부의 데이터)를 객채화
    @Autowired
    JwUtil jwt;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1. @Auth 요청을 처리할 어노테이션이 있는지 확인
        // HTTP 요청을 처리하는 메서드인지 확인
        if(handler instanceof HandlerMethod){
            // 형변환
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();


            // 2. 인증토큰 읽어오기
            // @Auth 어노테이션이 있는지 확인
            Auth auth = method.getAnnotation(Auth.class);

            // @Auth 어노테이션이 없으면 토큰 관련처리를 하지 않음
            if(auth == null){
                return true;
            }


            String token = request.getHeader("Authorization");
            // 인증 토큰이 없으면
            if(token == null || token.isEmpty()){
                // 401 : Unauthorized(미인증이라는 의미로 사용)
                response.setStatus(401);
                return false;
            }

            // 인증 토큰이 있으면
            // 3. 인증토큰 및 페이로드(subject,claim)데이터 객체화
            AuthProfile profile = jwt.validateToken(token.replace("Bearer ",""));
            if(profile == null){
                response.setStatus(401);
                return false;
            }


            // 4. 요청 속성(attribute)에 프로필 객체 추가하기
            request.setAttribute("authProfile", profile);
            return true;

        }

        return true;
    }

}
