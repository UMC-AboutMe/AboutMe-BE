package com.example.aboutme.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMyPageDTO {
        @JsonProperty("my_info")
        private MyPageInfo myPageInfo;

        @JsonProperty("insight")
        private MyPageInsight myPageInsight;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageInfo{
        @JsonProperty("profile_name")
        private String profileName;

        @JsonProperty("space_name")
        private String spaceName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageInsight{
        @JsonProperty("profile_shared_num")
        private int profileSharedNum;

        @JsonProperty("space_shared_num")
        private int spaceSharedNum;
    }
}
