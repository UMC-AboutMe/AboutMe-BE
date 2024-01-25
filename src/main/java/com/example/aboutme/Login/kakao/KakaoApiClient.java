package com.example.aboutme.Login.kakao;

import com.example.aboutme.Login.oauth.OAuthAPIClient;
import com.example.aboutme.Login.oauth.OAuthInfoResponse;
import com.example.aboutme.Login.oauth.OAuthLoginParams;
import com.example.aboutme.domain.constant.Social;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthAPIClient {
    private static final String GRANT_TYPE = "authorization_code";

    private String authUrl = "https://kauth.kakao.com";

    private String apiUrl = "https://kapi.kakao.com";

    private String clientId = "f9951615adf3d33e15c6b19ce20eeb6c";

    private final RestTemplate restTemplate;

    @Override
    public Social OauthProvider() {
        return Social.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth/token";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);

        assert response != null;
        return response.getAccess_token();
    }

    @Override
    public OAuthInfoResponse requestOAuthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\",\"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}
