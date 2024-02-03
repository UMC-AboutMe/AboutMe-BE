package com.example.aboutme.converter;

import com.example.aboutme.app.dto.SocialInfoRequest;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;

public class MemberConverter {
    public static Member toMember(SocialInfoRequest.KakaoDTO kakaoDTO, Social social, String token){
        return Member.builder().email(kakaoDTO.getEmail())
                .social(social)
                .jwtAccessToken(token).build();
    }
    public static Member toMember(SocialInfoRequest.GoogleDTO googleDTO, Social social, String token){
        return Member.builder().email(googleDTO.getEmail())
                .social(social)
                .jwtAccessToken(token).build();
    }

}
