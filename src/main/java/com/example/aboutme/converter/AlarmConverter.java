package com.example.aboutme.converter;

import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.domain.*;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;

import java.util.List;

public class AlarmConverter {
    
    public static Alarm toAlarm(Member member, Space space, String spaceNickname) {

        return Alarm.builder()
                .member(member)
                .content(String.format("%s님이 스페이스를 공유하셨습니다", spaceNickname))
                .isRead(false)
                .profile(null)
                .space(space)
                .build();
    }

    public static AlarmResponse.JoinResultDTO toJoinResultDTO(Alarm newAlarm) {
        return AlarmResponse.JoinResultDTO.builder()
                .content(newAlarm.getContent())
                .isRead(newAlarm.isRead())
                .subscriberNickname(newAlarm.getMember().getEmail())
                .build();
    }

    public static AlarmResponse.GetAlarmListDTO toGetAlarmListDTO(List<Alarm> alarmList) {

        List<AlarmResponse.GetAlarmDTO> alarmDTOList = alarmList.stream()
                .map(alarm -> {
                    Profile profile = alarm.getProfile();
                    Integer profileSerialNumber = profile != null ? profile.getSerialNumber() : null;
                    Space space = alarm.getSpace();
                    Long spaceId = space != null ? space.getId() : null;
                    return AlarmResponse.GetAlarmDTO.builder()
                            .content(alarm.getContent())
                            .profileSerialNumber(profileSerialNumber)
                            .spaceId(spaceId)
                            .build();
                }).toList();

        return new AlarmResponse.GetAlarmListDTO(alarmDTOList);
    }


    public static Alarm toProfileAlarm(Member member, Profile profile, String profileName) {
        return Alarm.builder()
                .content(String.format("%s님이 프로필을 공유하셨습니다", profileName))
                .isRead(false)
                .member(member)
                .profile(profile)
                .space(null)
                .build();
    }
}
