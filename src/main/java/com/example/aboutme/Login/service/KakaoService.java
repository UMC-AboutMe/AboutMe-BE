package com.example.aboutme.Login.service;

import com.example.aboutme.Login.dto.KakaoDTO;

public interface KakaoService {
    String getKakaoLogin();
    KakaoDTO getKakaoInfo(String code) throws Exception;
    KakaoDTO getUserInfoWithToken(String accessToken) throws Exception;
    void saveKakaoMember(KakaoDTO kakaoDTO);
}
