package com.example.aboutme.Login.oauth;

import com.example.aboutme.domain.constant.Social;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    Social OauthProvider();
    MultiValueMap<String, String> makeBody();
}
