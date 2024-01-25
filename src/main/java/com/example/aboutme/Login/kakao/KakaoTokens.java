package com.example.aboutme.Login.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokens {
    @JsonProperty("access_token")
    private String access_token;


    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("expire_in")
    private String expire_in;

    @JsonProperty("refresh_token_expire_in")
    private String refresh_token_expire_in;

    @JsonProperty("scope")
    private String scope;
}
