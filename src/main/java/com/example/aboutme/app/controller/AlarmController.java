package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.service.AlarmService.AlarmService;
import com.example.aboutme.validation.annotation.ExistProfileBySerialNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alarms")
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    /**
     * [GET] /alarms/myprofiles
     * 프로필 알람 데이터 조회
     * @param memberId 멤버 식별자
     * @return
     */
    @GetMapping("/myprofiles")
    public ApiResponse<AlarmResponse.GetProfileAlarmListDTO> getProfileAlarm(@RequestHeader("member-id") Long memberId){

        List<Alarm> alarmList = alarmService.getProfileAlarm(memberId);

        log.info("프로필 알람 데이터 조회: member={}", memberId);

        return ApiResponse.onSuccess(AlarmConverter.toGetProfileAlarmListDTO(alarmList));
    }
}
