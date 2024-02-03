package com.example.aboutme.Login;

import com.example.aboutme.Login.dto.SocialInfoRequest;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;

public class MemberConverter {
    public static Member toMember(SocialInfoRequest.KakaoDTO kakaoDTO, Social social, String token){
        return Member.builder().email(kakaoDTO.getEmail())
                .social(social)
                .jwtAccessToken(token).build();
    }
    public static Member toMember(SocialInfoRequest.GoogleDTO googleDTO, Social social){
        return Member.builder().email(googleDTO.getEmail())
                .social(social).build();
    }

}
