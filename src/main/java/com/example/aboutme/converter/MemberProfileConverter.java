package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.domain.Member;
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

    public static MemberProfileResponse.MemberProfileDTO toMemberProfileDTO(MemberProfile memberProfile) {
        return MemberProfileResponse.MemberProfileDTO.builder()
                .id(memberProfile.getId())
                .favorite(memberProfile.getFavorite())
                .member(MemberProfileResponse.StorageMemberDTO.builder()
                        .email(memberProfile.getMember().getEmail())
                        .social(memberProfile.getMember().getSocial()).build())
                .profile(MemberProfileResponse.StorageProfileDTO.builder()
                        .id(memberProfile.getProfile().getId())
                        .serialNumber(memberProfile.getProfile().getSerialNumber())
                        .isDefault(memberProfile.getProfile().getIsDefault())
                        .build())
                .build();
    }
    public static MemberProfileResponse.favoriteDto toToggleFavorite(Boolean favorite) {
        return MemberProfileResponse.favoriteDto.builder()
                .favorite(favorite)
                .build();
    }

    public static MemberProfile toMemberProfile(Member member, Profile profile){
        return MemberProfile.builder()
                .favorite(false)
                .member(member)
                .profile(profile)
                .build();
    }

    public static MemberProfileResponse.DeleteMemberProfileMsgDTO toDeleteMemberProfileMsgDTO(Long profileId, String msg){
        return MemberProfileResponse.DeleteMemberProfileMsgDTO.builder()
                .msg(msg)
                .memberProfileId(profileId)
                .build();
    }
}
