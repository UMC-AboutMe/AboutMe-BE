package com.example.aboutme.converter;

import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileImage;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.constant.ProfileImageType;

public class ProfileImageConverter {

    public static ProfileImage toProfileImage(Profile profile, Space space){
        return ProfileImage.builder()
                .type(ProfileImageType.NONE)
                .profile(profile)
                .space(space)
                .build();
    }
}
