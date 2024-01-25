package com.example.aboutme.service.auth;

import com.example.aboutme.domain.constant.Social;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<Social, OAuthAPIClient> clients;

    public RequestOAuthInfoService(List<OAuthAPIClient> clients) {
        this.clients = clients.stream().collect(Collectors.toUnmodifiableMap(OAuthAPIClient::OauthProvider, Function.identity()));
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthAPIClient client = clients.get(params.OauthProvider());

        String accessToken = client.requestAccessToken(params);
        return client.requestOAuthInfo(accessToken);
    }
}
