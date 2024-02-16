package com.example.aboutme.converter;

import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.app.dto.SocialInfoRequest;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;

public class AlarmConverter {
    public static Alarm toAlarm(Member member, String spaceNickname){
        return Alarm.builder()
                .member(member)
                .content(String.format("%s 님이 스페이스를 공유하셨습니다", spaceNickname))
                .isRead(false)
                .build();
    }

    public static AlarmResponse.ShareSpaceResultDTO toShareSpaceResultDTO(Alarm newAlarm, String spaceNickname) {
        return AlarmResponse.ShareSpaceResultDTO.builder()
                .content(newAlarm.getContent())
                .isRead(newAlarm.isRead())
                .subscriberNickname(newAlarm.getMember().getEmail())
                .sharedSpaceNickname(spaceNickname)
                .build();
    }
}
