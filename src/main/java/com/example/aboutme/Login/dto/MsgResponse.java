package com.example.aboutme.Login.dto;

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
        String msg;
        String email;
        String token;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class validMsgDTO {
        private String msg;
    }
}
