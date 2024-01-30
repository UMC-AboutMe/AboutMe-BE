package com.example.aboutme.app.dto;

import com.example.aboutme.validation.annotation.ExistProfileFeature;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotEmpty;

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
}
