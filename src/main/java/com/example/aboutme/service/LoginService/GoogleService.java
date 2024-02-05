package com.example.aboutme.service.LoginService;

import com.example.aboutme.app.dto.SocialInfoRequest;
import com.example.aboutme.domain.Member;

public interface GoogleService {
    String getGoogleLogin();
    SocialInfoRequest.GoogleDTO getGoogleInfo(String code) throws Exception;
    SocialInfoRequest.GoogleDTO getUserInfoWithToken(String accessToken) throws Exception;
    Member saveGoogleMember(SocialInfoRequest.GoogleDTO googleDTO);
}
