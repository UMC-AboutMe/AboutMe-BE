package com.example.aboutme.Login.jwt;


import com.example.aboutme.domain.constant.Social;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.InitializingBean;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {
    @Value("${spring.security.jwt.secret}")
    private String secret;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 10000000 * 1000 * 60 * 3000;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
//    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    private Key key;
    private static final String AUTHORITIES_KEY = "auth";
    long now = (new Date()).getTime();
    Date accessTokenExpireTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    Date refreshTokenExpireTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

    public String createToken(String email, String social){
        String newToken= Jwts.builder().
                setSubject(email).
                setIssuer(social).
                signWith(key, SignatureAlgorithm.HS512).compact();

//        return JwtDTO.builder()
//                .accessToken(newToken)
//                .expireIn(ACCESS_TOKEN_EXPIRE_TIME/1000).build();
        return  newToken;
    }

    public boolean validateToken(String userToken){
        try {
            System.out.println(userToken);
            Jwts.parser().setSigningKey(key).parseClaimsJws(userToken);
            System.out.println("Success valid");
            return true;
        } catch (SecurityException| MalformedJwtException e) {
            return false;
        }catch (UnsupportedJwtException e){
            return false;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts
//                .parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        User principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.get("iss", String.class));
        return claims.get("sub", String.class);
    }

    public String getSocialFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("iss", String.class);
    }

    public TokenDTO.tokenClaimsDTO getTokenInfoFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims.get("iss", String.class) == "KAKAO"){
            return TokenDTO.tokenClaimsDTO.builder()
                    .email(claims.get("sub", String.class))
                    .social(Social.KAKAO)
                    .build();
        }else{
            return TokenDTO.tokenClaimsDTO.builder()
                    .email(claims.get("sub", String.class))
                    .social(Social.GOOGLE)
                    .build();
        }
    }


}
