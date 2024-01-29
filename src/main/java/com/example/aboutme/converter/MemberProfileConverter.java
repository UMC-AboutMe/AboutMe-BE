package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberProfileResponse;

public class MemberProfileConverter {

    public static MemberProfileResponse.favoriteDto toToggleFavorite(Boolean favorite) {
        return MemberProfileResponse.favoriteDto.builder()
                .favorite(favorite)
                .build();
    }
}
