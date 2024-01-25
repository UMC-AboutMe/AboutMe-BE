package com.example.aboutme.Login.kakao;

import com.example.aboutme.Login.oauth.OAuthLoginParams;
import com.example.aboutme.domain.constant.Social;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {

    private String authorizationCode;
    private String state;

    @Override
    public Social OauthProvider() {
        return Social.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
