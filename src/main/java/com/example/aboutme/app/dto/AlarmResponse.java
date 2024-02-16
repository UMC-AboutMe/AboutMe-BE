package com.example.aboutme.app.dto;

import com.example.aboutme.domain.Plan;
import com.example.aboutme.domain.constant.Mood;
import lombok.*;

import java.util.List;

public class AlarmResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShareSpaceResultDTO {
        private String content;
        private boolean isRead;
        private String subscriberNickname;
        private String sharedSpaceNickname;
    }
}
