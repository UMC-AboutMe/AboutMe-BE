package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.SocialInfoDTO;

public interface KakaoService {
    String getKakaoLogin();
    SocialInfoDTO.KakaoDTO getKakaoInfo(String code) throws Exception;
    SocialInfoDTO.KakaoDTO getUserInfoWithToken(String accessToken) throws Exception;
    void saveKakaoMember(SocialInfoDTO.KakaoDTO kakaoDTO);
}
