package com.example.aboutme.service.LoginService;

import com.example.aboutme.app.dto.SocialInfoRequest;

public interface KakaoService {
    String getKakaoLogin();
    SocialInfoRequest.KakaoDTO getKakaoInfo(String code) throws Exception;
    SocialInfoRequest.KakaoDTO getUserInfoWithToken(String accessToken) throws Exception;
    String saveKakaoMember(SocialInfoRequest.KakaoDTO kakaoDTO);
}
