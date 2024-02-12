package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MyPageResponse;
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

    public static MyPageResponse.GetMyPageDTO toGetMyPageDTO (String profileName, String spaceName, int profileSharedNum, int spaceSharedNum){
        MyPageResponse.MyPageInfo myPageInfo = MyPageResponse.MyPageInfo.builder()
                .profileName(profileName)
                .spaceName(spaceName)
                .build();

        MyPageResponse.MyPageInsight myPageInsight = MyPageResponse.MyPageInsight.builder()
                .profileSharedNum(profileSharedNum)
                .spaceSharedNum(spaceSharedNum)
                .build();

        return MyPageResponse.GetMyPageDTO.builder()
                .myPageInfo(myPageInfo)
                .myPageInsight(myPageInsight)
                .build();
    }
}
