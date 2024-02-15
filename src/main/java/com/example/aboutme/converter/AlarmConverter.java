package com.example.aboutme.converter;

import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Profile;

import java.util.List;


public class AlarmConverter {

    public static AlarmResponse.GetProfileAlarmListDTO toGetProfileAlarmListDTO(List<Alarm> alarmList){
        List<AlarmResponse.GetProfileAlarmDTO> profileAlarmDTOList = alarmList.stream()
                .map(alarm -> {
                    Profile profile = alarm.getProfile();
                    Integer profileSerialNumber = profile.getSerialNumber();

                    return AlarmResponse.GetProfileAlarmDTO.builder()
                            .content(alarm.getContent())
                            .profileSerialNumber(profileSerialNumber)
                            .build();
                })
                .toList();

        return new AlarmResponse.GetProfileAlarmListDTO(profileAlarmDTOList);
    }
}