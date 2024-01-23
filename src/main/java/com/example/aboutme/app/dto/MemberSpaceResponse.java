package com.example.aboutme.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSpaceResponse {

    private List<ListDto> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ListDto {
        private Long spaceId;
        private String nickname;
        private Integer characterType;
        private Integer roomType;
        private Boolean favorite;
    }

}
