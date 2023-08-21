package com.yja.myapp.auth.utill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yja.myapp.auth.AuthProfile;

import java.util.Date;

public class JwUtil {

    public String secret = "your-secret";
    public final int TOKEN_TIMEOUT =  1000 * 60 * 24 * 7;

    public String createToken(long id, String username, String nickname){

        Date now = new Date();

        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(String.valueOf(id))
                .withClaim("username",username)
                .withIssuedAt(now).withExpiresAt(exp).sign(algorithm);
    }

    public AuthProfile validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            Long id = Long.valueOf(decodedJWT.getSubject());
            String username = decodedJWT.getClaim("username").asString();

            return AuthProfile.builder().id(id).username(username).build();
        } catch (JWTVerificationException e){
            return null;

        }

    }
}
