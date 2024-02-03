package com.example.aboutme.service.LoginService;

import com.example.aboutme.app.dto.SocialInfoRequest;
import com.example.aboutme.domain.Member;

public interface KakaoService {
    String getKakaoLogin();
    SocialInfoRequest.KakaoDTO getKakaoInfo(String code) throws Exception;
    SocialInfoRequest.KakaoDTO getUserInfoWithToken(String accessToken) throws Exception;
    Member saveKakaoMember(SocialInfoRequest.KakaoDTO kakaoDTO);
}
