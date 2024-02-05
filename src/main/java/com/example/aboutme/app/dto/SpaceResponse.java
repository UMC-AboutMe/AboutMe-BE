package com.example.aboutme.app.dto;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Plan;
import com.example.aboutme.domain.SpaceImage;
import com.example.aboutme.domain.constant.Mood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SpaceResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        private String nickname;
        private Integer characterType;
        private Integer roomType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadResultDTO {
        private String nickname;
        private Integer characterType;
        private Integer roomType;
        private Mood mood;
        private String musicUrl;
        private String statusMessage;
        private List<String> spaceImageList;
        private List<PlanResponse.planDTO> planList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDTO {
        private String nickname;
        private Integer characterType;
        private Integer roomType;
        private Mood mood;
        private String musicUrl;
        private String statusMessage;
        private List<String> spaceImageList;
        private List<Plan> planList;
    }
}
