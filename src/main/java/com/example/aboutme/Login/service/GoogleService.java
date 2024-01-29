package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.GoogleDTO;

public interface GoogleService {
    String getGoogleLogin();
    GoogleDTO getGoogleInfo(String code) throws Exception;
    GoogleDTO getUserInfoWithToken(String accessToken) throws Exception;
    void saveGoogleMember(GoogleDTO googleDTO);
}
