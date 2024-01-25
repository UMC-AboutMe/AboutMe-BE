package com.example.aboutme.Login.oauth;

import com.example.aboutme.domain.constant.Social;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    Social getOAuthProvider();
}
