package com.example.aboutme.app.dto;

import lombok.Getter;

public class SpaceRequest {
    @Getter
    public static class JoinDTO {
        private String nickname;
        private Integer characterType;
        private Integer roomType;
    }
}
