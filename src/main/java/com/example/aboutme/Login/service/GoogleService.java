package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.SocialInfoDTO;

public interface GoogleService {
    String getGoogleLogin();
    SocialInfoDTO.GoogleDTO getGoogleInfo(String code) throws Exception;
    SocialInfoDTO.GoogleDTO getUserInfoWithToken(String accessToken) throws Exception;
    void saveGoogleMember(SocialInfoDTO.GoogleDTO googleDTO);
}
