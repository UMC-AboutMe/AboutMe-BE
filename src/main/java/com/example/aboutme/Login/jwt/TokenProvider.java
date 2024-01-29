package com.example.aboutme.Login.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final String secret = "a0484ca954f0b57df32c48691a9570cc01f6117ac8f270b89c33ab84cd53fd15e095d443dd06ea25f3860b4f70762ec53941a46826cbe112c5c7e3ea6cb65d05";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

    long now = (new Date()).getTime();
    Date accessTokenExpireTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    Date refreshTokenExpireTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

    public JwtDTO createToken(String email){
        String newToken= Jwts.builder().
                setSubject(email).
                setExpiration(accessTokenExpireTime).
                signWith(key, SignatureAlgorithm.HS512).compact();

        return JwtDTO.builder()
                .accessToken(newToken)
                .expireIn(ACCESS_TOKEN_EXPIRE_TIME/1000).build();
    }


}
