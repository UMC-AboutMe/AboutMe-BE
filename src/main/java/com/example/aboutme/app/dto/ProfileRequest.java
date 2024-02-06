package com.example.aboutme.app.dto;

import com.example.aboutme.domain.constant.ProfileImageType;
import com.example.aboutme.validation.annotation.ExistProfileFeature;
import com.example.aboutme.validation.annotation.ExistProfilesBySerialNum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ProfileRequest {

    @Getter
    public static class CreateProfileDTO{
        @NotEmpty
        private String name;
    }

    @Getter
    public static class UpdateProfileDTO{
        @JsonProperty("feature_id")
        @ExistProfileFeature
        private Long featureId;

        @JsonProperty("feature_key")
        private String featureKey;

        @JsonProperty("feature_value")
        private String featureValue;
    }

    @Getter
    public static class ShareProfileDTO{
        @JsonProperty("profile_serial_numbers")
        @ExistProfilesBySerialNum
        private List<Integer> profileSerialNumberList;
    }

    @Getter
    public static class UpdateProfileImageDTO{
        @JsonProperty("profile_image_type")
        private ProfileImageType profileImageType;
    }
}
