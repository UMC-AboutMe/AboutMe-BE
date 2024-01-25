package com.example.aboutme.Login.oauth;

import com.example.aboutme.domain.constant.Social;

public interface OAuthAPIClient {
    //클라 타입 반환
    Social OauthProvider();

    //토큰 발급부분
    String requestAccessToken(OAuthLoginParams params);

    //accesstoken으로  profile info 가져오기
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
