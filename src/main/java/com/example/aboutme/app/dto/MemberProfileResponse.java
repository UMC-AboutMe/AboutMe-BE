package com.example.aboutme.app.dto;

import com.example.aboutme.domain.constant.Social;
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
        private StorageMemberDTO member;

        @JsonProperty("profile")
        private StorageProfileDTO profile;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteMemberProfileMsgDTO {
        private String msg;
        private Long memberProfileId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class favoriteDto {
        private Boolean favorite;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StorageMemberDTO {
        @JsonProperty("social")
        private Social social;

        @JsonProperty("email")
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StorageProfileDTO {
        @JsonProperty("profile_id")
        private Long id;

        @JsonProperty("serial_number")
        private int serialNumber;

        @JsonProperty("is_default")
        private Boolean isDefault;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchMemberProfileListDTO{
        private List<MemberProfileResponse.SearchMemberProfileDTO> memberProfileList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchMemberProfileDTO{
        private Long profileId;
        private String profileName;
        private Boolean favorite;
    }
}
