package com.example.aboutme.app.dto;

import com.example.aboutme.validation.annotation.CharacterTypeBoundary;
import com.example.aboutme.validation.annotation.RoomTypeBoundary;
import lombok.Getter;

public class SpaceRequest {
    @Getter
    public static class JoinDTO {
        private String nickname;

        @CharacterTypeBoundary
        private Integer characterType;

        @RoomTypeBoundary
        private Integer roomType;
    }
}
