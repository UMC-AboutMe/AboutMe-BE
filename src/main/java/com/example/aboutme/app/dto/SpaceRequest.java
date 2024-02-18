package com.example.aboutme.app.dto;

import com.example.aboutme.domain.constant.Mood;
import com.example.aboutme.validation.annotation.CharacterTypeBoundary;
import com.example.aboutme.validation.annotation.ExistMood;
import com.example.aboutme.validation.annotation.ExistSpaceNickname;
import com.example.aboutme.validation.annotation.RoomTypeBoundary;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class SpaceRequest {
    @Getter
    public static class JoinDTO {
        @ExistSpaceNickname
        private String nickname;

        @CharacterTypeBoundary
        private Integer characterType;

        @RoomTypeBoundary
        private Integer roomType;
    }

    @Getter
    public static class UpdateDTO {
        @ExistSpaceNickname
        private String nickname;

        @CharacterTypeBoundary
        private Integer characterType;

        @RoomTypeBoundary
        private Integer roomType;
        
        @ExistMood
        private String mood;

        private String musicUrl;

        private String statusMessage;
    }
}
