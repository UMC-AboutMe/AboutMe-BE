package com.example.aboutme.app.dto;

import com.example.aboutme.domain.Member;
import com.example.aboutme.validation.annotation.CharacterTypeBoundary;
import com.example.aboutme.validation.annotation.ExistMember;
import com.example.aboutme.validation.annotation.ExistMood;
import com.example.aboutme.validation.annotation.RoomTypeBoundary;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class AlarmRequest {
    @Getter
    public static class CreateDTO {
        @ExistMember
        private long toMemberId;
    }
}
