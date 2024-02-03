package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.SocialInfoRequest;

public interface GoogleService {
    String getGoogleLogin();
    SocialInfoRequest.GoogleDTO getGoogleInfo(String code) throws Exception;
    SocialInfoRequest.GoogleDTO getUserInfoWithToken(String accessToken) throws Exception;
    String saveGoogleMember(SocialInfoRequest.GoogleDTO googleDTO);
}
