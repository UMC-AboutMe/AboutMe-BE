package com.example.aboutme.app.dto;

import lombok.Getter;

public class MemberProfileRequest {
    @Getter
    public static class DeleteMemberDTO {
        private Long profileId;
    }
}
