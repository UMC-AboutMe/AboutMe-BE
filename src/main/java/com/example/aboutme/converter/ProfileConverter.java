package com.example.aboutme.converter;

import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.constant.Side;

import java.util.ArrayList;
import java.util.List;

public class ProfileConverter {

    public static Profile toProfile(Integer serialNumber){
        return Profile.builder()
                .isDefault(false)
                .serialNumber(serialNumber)
                .profileFeatureList(new ArrayList<>())
                .build();
    }

    public static ProfileResponse.CreateProfileDTO toCreateProfileDTO(Profile profile){
        return ProfileResponse.CreateProfileDTO.builder()
                .id(profile.getId())
                .serialNumber(profile.getSerialNumber())
                .isDefault(profile.getIsDefault())
                .profileFeatureFrontList(profile.getProfileFeatureList().stream()
                        .filter(profileFeature -> profileFeature.getSide()== Side.FRONT)
                        .map(ProfileConverter::toProfileFeatureDTO)
                        .toList())
                .profileFeatureBackList(profile.getProfileFeatureList().stream()
                        .filter(profileFeature -> profileFeature.getSide()== Side.BACK)
                        .map(ProfileConverter::toProfileFeatureDTO)
                        .toList())
                .build();
    }

    private static ProfileResponse.ProfileFeatureDTO toProfileFeatureDTO(ProfileFeature profileFeature){
        return ProfileResponse.ProfileFeatureDTO.builder()
                .id(profileFeature.getId())
                .key(profileFeature.getProfileKey())
                .value(profileFeature.getProfileValue())
                .build();
    }
}
