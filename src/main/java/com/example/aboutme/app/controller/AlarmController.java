package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.AlarmResponse;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.service.AlarmService.AlarmService;
import com.example.aboutme.service.MemberService.MemberService;
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
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    /**
     * [GET] /alarms
     * 알람 데이터 조회
     * @param token 토큰
     * @return
     */
    @GetMapping
    public ApiResponse<AlarmResponse.GetAlarmListDTO> getProfileAlarm(@RequestHeader("token") String token){

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        List<Alarm> alarmList = alarmService.getAlarmList(member.getId());

        log.info("프로필 알람 데이터 조회: member={}", member.getId());

        return ApiResponse.onSuccess(AlarmConverter.toGetAlarmListDTO(alarmList));
    }

    /**
     * [DELETE] /alarms
     * 알람 데이터 삭제
     * @param token 토큰
     * @param alarmId 알림 식별자
     * @return
     */
    @DeleteMapping("/{alarmId}")
    public ApiResponse<Void> deleteProfileAlarm(@RequestHeader("token") String token, @PathVariable Long alarmId){

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        alarmService.deleteAlarm(member, alarmId);

        return ApiResponse.onSuccess(null);
    }
}
