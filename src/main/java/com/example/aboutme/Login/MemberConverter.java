package com.example.aboutme.Login;

import com.example.aboutme.Login.dto.KakaoDTO;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;

public class MemberConverter {
    public static Member toMember(KakaoDTO kakaoDTO, Social social){
        return Member.builder().Email(kakaoDTO.getEmail())
                .social(social).build();
    }
}
