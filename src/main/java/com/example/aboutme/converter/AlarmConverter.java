package com.example.aboutme.converter;

import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;


public class AlarmConverter {
    public static Alarm toProfileAlarm(Member member, Profile profile, String profileName){
        return Alarm.builder()
                .content(String.format("%s님이 프로필을 공유하셨습니다", profileName))
                .isRead(false)
                .member(member)
                .profile(profile)
                .build();
    }
}