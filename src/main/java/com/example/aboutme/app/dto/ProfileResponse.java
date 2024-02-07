package com.example.aboutme.app.dto;

import com.example.aboutme.domain.constant.ProfileImageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProfileResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProfileDTO{
        @JsonProperty("myprofile_id")
        private Long id;

        @JsonProperty("serial_number")
        private Integer serialNumber;

        @JsonProperty("is_default")
        private Boolean isDefault;

        @JsonProperty("front_features")
        private List<ProfileFeatureDTO> profileFeatureFrontList;

        @JsonProperty("back_features")
        private List<ProfileFeatureDTO> profileFeatureBackList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileFeatureDTO{
        @JsonProperty("feature_id")
        private Long id;
        private String key;
        private String value;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProfileListDTO{
        @JsonProperty("myprofiles")
        private List<ProfileDTO> profileDTOList;

        @JsonProperty("total_myprofile")
        private int totalProfile;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileDTO{
        @JsonProperty("profile_id")
        private Long id;

        @JsonProperty("serial_number")
        private int serialNumber;

        @JsonProperty("is_default")
        private Boolean isDefault;

        @JsonProperty("profile_img_url")
        private String profileImageUrl;

        @JsonProperty("front_features")
        private List<ProfileFeatureDTO> frontFeatureList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProfileDTO{
        @JsonProperty("feature_id")
        private Long featureId;

        @JsonProperty("feature_key")
        private String featureKey;

        @JsonProperty("feature_value")
        private String featureValue;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMyProfileDTO{
        @JsonProperty("profile_id")
        private Long profileId;

        @JsonProperty("serial_number")
        private int serialNumber;

        @JsonProperty("is_default")
        private Boolean isDefault;

        @JsonProperty("profile_img_url")
        private String profileImageUrl;

        @JsonProperty("front_features")
        private List<ProfileFeatureDTO> frontFeatureList;

        @JsonProperty("back_features")
        private List<ProfileFeatureDTO> backFeatureList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateMyProfileImageDTO {

        private ProfileImageType type;

        @JsonProperty("characterType")
        private Integer characterType;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateDefaultProfileDTO{
        @JsonProperty("profile_id")
        private Long id;

        @JsonProperty("serial_number")
        private int serialNumber;

        @JsonProperty("is_default")
        private Boolean isDefault;
    }
}
