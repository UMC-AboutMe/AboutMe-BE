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

    public static ProfileResponse.GetProfileListDTO toGetProfileListDTO(List<Profile> profileList){
        List<ProfileResponse.ProfileDTO> profileDTOList = profileList.stream()
                .map(ProfileConverter::toProfileDTO)
                .toList();

        return ProfileResponse.GetProfileListDTO.builder()
                .profileDTOList(profileDTOList)
                .totalProfile(profileList.size())
                .build();
    }

    private static ProfileResponse.ProfileDTO toProfileDTO(Profile profile){
        return ProfileResponse.ProfileDTO.builder()
                .id(profile.getId())
                .serialNumber(profile.getSerialNumber())
                .isDefault(profile.getIsDefault())
                .profileImageUrl(null) // 아직 미설정
                .frontFeatureList(profile.getProfileFeatureList().stream()
                        .filter(profileFeature -> profileFeature.getSide()== Side.FRONT)
                        .map(ProfileConverter::toProfileFeatureDTO)
                        .toList())
                .backFeatureList(profile.getProfileFeatureList().stream()
                        .filter(profileFeature -> profileFeature.getSide()== Side.BACK)
                        .map(ProfileConverter::toProfileFeatureDTO)
                        .toList())
                .build();
    }

    public static ProfileResponse.UpdateProfileDTO toUpdateProfileDTO(ProfileFeature profileFeature){
        return ProfileResponse.UpdateProfileDTO.builder()
                .featureId(profileFeature.getId())
                .featureKey(profileFeature.getProfileKey())
                .featureValue(profileFeature.getProfileValue())
                .build();
    }
}
