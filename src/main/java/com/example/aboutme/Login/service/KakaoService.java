package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.SocialInfoRequest;

public interface KakaoService {
    String getKakaoLogin();
    SocialInfoRequest.KakaoDTO getKakaoInfo(String code) throws Exception;
    SocialInfoRequest.KakaoDTO getUserInfoWithToken(String accessToken) throws Exception;
    String saveKakaoMember(SocialInfoRequest.KakaoDTO kakaoDTO);
}
