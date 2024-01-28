package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.constant.Side;
import com.example.aboutme.domain.mapping.MemberProfile;

import java.util.List;

public class MemberProfileConverter {
    public static MemberProfileResponse.GetMemberProfileListDTO toGetMemberProfileListDTO(List<MemberProfile> memberProfileList){
        List<MemberProfileResponse.MemberProfileDTO> memberProfileDTOList = memberProfileList.stream()
                .map(MemberProfileConverter::toMemberProfileDTO)
                .toList();

        return MemberProfileResponse.GetMemberProfileListDTO.builder()
                .memberProfileDTOList(memberProfileDTOList)
                .totalMemberProfile(memberProfileList.size())
                .build();
    }

    public static MemberProfileResponse.MemberProfileDTO toMemberProfileDTO(MemberProfile memberProfile){
        return MemberProfileResponse.MemberProfileDTO.builder()
                .id(memberProfile.getId())
                .favorite(memberProfile.getFavorite())
                .member(memberProfile.getMember())
                .profile(memberProfile.getProfile())
                .build();
    }
}
