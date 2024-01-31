package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;

public class MemberProfileConverter {

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
}
