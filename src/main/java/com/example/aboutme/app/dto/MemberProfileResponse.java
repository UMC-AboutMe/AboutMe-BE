package com.example.aboutme.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberProfileResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class favoriteDto {
        private Boolean favorite;
    }
}
