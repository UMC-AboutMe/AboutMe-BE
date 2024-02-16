package com.example.aboutme.converter;

import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;

public class AlarmConverter {

    public static Alarm toAlarm(Member member, String spaceNickname){
        return Alarm.builder()
                .member(member)
                .content(String.format("%s 님이 스페이스를 공유하셨습니다", spaceNickname))
                .isRead(false)
                .build();
    }

    public static AlarmResponse.JoinResultDTO toJoinResultDTO(Alarm newAlarm) {
        return AlarmResponse.JoinResultDTO.builder()
                .content(newAlarm.getContent())
                .isRead(newAlarm.isRead())
                .subscriberNickname(newAlarm.getMember().getEmail())
                .build();
    }
    public static Alarm toProfileAlarm(Member member, Profile profile, String profileName){
        return Alarm.builder()
                .content(String.format("%s님이 프로필을 공유하셨습니다", profileName))
                .isRead(false)
                .member(member)
                .profile(profile)
                .build();
    }
}