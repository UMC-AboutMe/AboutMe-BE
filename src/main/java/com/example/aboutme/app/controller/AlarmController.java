package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.service.AlarmService.AlarmService;
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
     * [GET] /alarms
     * 알람 데이터 조회
     * @param memberId 멤버 식별자
     * @return
     */
    @GetMapping
    public ApiResponse<AlarmResponse.GetAlarmListDTO> getProfileAlarm(@RequestHeader("member-id") Long memberId){

        List<Alarm> alarmList = alarmService.getAlarmList(memberId);

        log.info("프로필 알람 데이터 조회: member={}", memberId);

        return ApiResponse.onSuccess(AlarmConverter.toGetAlarmListDTO(alarmList));
    }
}
