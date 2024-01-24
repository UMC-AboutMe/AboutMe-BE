package com.example.aboutme.app.dto;

import com.example.aboutme.domain.ProfileFeature;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
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
}
