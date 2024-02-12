package com.example.aboutme.app.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SocialInfoRequest {
    @Builder
    @Data
    public static class KakaoDTO{
        private long id;
        private String email;
        private String nickname;
    }

    @Builder
    @Data
    public static class GoogleDTO{
        private String email;
    }

}
