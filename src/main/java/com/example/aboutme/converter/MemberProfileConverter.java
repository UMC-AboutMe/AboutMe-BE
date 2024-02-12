package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.mapping.MemberProfile;

import java.util.List;
import java.util.stream.Collectors;

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

    public static MemberProfileResponse.SearchMemberProfileListDTO toSearchMemberProfileListDTO(List<MemberProfile> memberProfileList){
        List<MemberProfileResponse.SearchMemberProfileDTO> memberProfileListDto =
                memberProfileList.stream()
                        .map(memberProfile -> {
                            String profileValue = memberProfile.getProfile().getProfileFeatureList().stream()
                                    .filter(profileFeature -> "name".equals(profileFeature.getProfileKey()))
                                    .map(ProfileFeature::getProfileValue)
                                    .findFirst()
                                    .orElse("");

                            return MemberProfileResponse.SearchMemberProfileDTO.builder()
                                    .profileId(memberProfile.getProfile().getId())
                                    .profileName(profileValue)
                                    .favorite(memberProfile.getFavorite())
                                    .image(ProfileConverter.toProfileImageDTO(memberProfile.getProfile().getProfileImage()))
                                    .build();
                        })
                        .collect(Collectors.toList());

        return new MemberProfileResponse.SearchMemberProfileListDTO(memberProfileListDto);
    }
}
