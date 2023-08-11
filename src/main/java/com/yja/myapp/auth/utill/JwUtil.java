package com.yja.myapp.auth.utill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yja.myapp.auth.AuthProfile;

import java.util.Date;

public class JwUtil {
    // 임의의 서명값
    public String secret = "your-secret";
    public final int TOKEN_TIMEOUT =  1000 * 60 * 24 * 7;

    public String createToken(long id, String username, String nickname){
        // 토큰 생성시간 만료시간
        Date now = new Date();

        // 만료시간 : 1차인증 이런 게 잘 걸려있으면 큰 문제는 안됨
        // 길게 잡으면 7~30일 / 보통은 1시간에서 3시간 / 짧게는 5~15분
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(String.valueOf(id))
                .withClaim("username",username)
                .withClaim("nickname", nickname)
                .withIssuedAt(now).withExpiresAt(exp).sign(algorithm);


    }

    public AuthProfile validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            // 토큰 검증 제대로 된 상황
            // 토큰 페이로드(데이터, )를 조회
            Long id = Long.valueOf(decodedJWT.getSubject());
            String nickname = decodedJWT.getClaim("nickname").asString();
            String username = decodedJWT.getClaim("username").asString();

            return AuthProfile.builder().id(id).username(username).nickname(nickname).build();
        } catch (JWTVerificationException e){
            // 토큰 검증 오류일 때 null로
            return null;

        }

    }
}
