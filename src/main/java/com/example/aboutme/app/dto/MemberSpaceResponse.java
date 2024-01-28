package com.example.aboutme.app.dto;

import com.example.aboutme.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class MemberSpaceResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetListDto{
        private List<MemberSpaceResponse.getDto> MemberSpaceList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class getDto {
        private Long spaceId;
        private String nickname;
        private Integer characterType;
        private Integer roomType;
        private Boolean favorite;
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
    public static class addDto {
        private Long spaceId;
    }
}
