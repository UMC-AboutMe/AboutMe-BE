package com.example.aboutme.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MsgResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginMsgDTO{
        private String msg;
        private String email;
        private String token;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class validMsgDTO {
        private String msg;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class unregisterMsgDTO {
        private Long memberId;
        private String msg;
    }
}
