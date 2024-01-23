package com.example.aboutme.app.dto;

import com.example.aboutme.validation.annotation.CharacterTypeBoundary;
import lombok.Getter;

public class SpaceRequest {
    @Getter
    public static class JoinDTO {
        private String nickname;

        @CharacterTypeBoundary
        private Integer characterType;

        private Integer roomType;
    }
}
