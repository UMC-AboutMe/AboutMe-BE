package com.example.aboutme.app.dto;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class MemberProfileResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMemberProfileListDTO{
        @JsonProperty("member_profiles")
        private List<MemberProfileResponse.MemberProfileDTO> memberProfileDTOList;

        @JsonProperty("total_member_profiles")
        private int totalMemberProfile;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberProfileDTO {
        @JsonProperty("member_profile_id")
        private Long id;

        @JsonProperty("favorite")
        private Boolean favorite;

        @JsonProperty("member")
        private Member member;

        @JsonProperty("profile")
        private Profile profile;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class favoriteDto {
        private Boolean favorite;
    }

}
