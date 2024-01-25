package com.example.aboutme.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expireIn;

    public static AuthTokens of(String accessToken, String refreshToken, String grantType, Long expireIn) {
        return new AuthTokens(accessToken, refreshToken, grantType, expireIn);
    }
}
