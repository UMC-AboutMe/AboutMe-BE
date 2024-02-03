package com.example.aboutme.service.LoginService;

import com.example.aboutme.app.dto.SocialInfoRequest;

public interface GoogleService {
    String getGoogleLogin();
    SocialInfoRequest.GoogleDTO getGoogleInfo(String code) throws Exception;
    SocialInfoRequest.GoogleDTO getUserInfoWithToken(String accessToken) throws Exception;
    String saveGoogleMember(SocialInfoRequest.GoogleDTO googleDTO);
}
