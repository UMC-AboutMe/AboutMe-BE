package com.example.aboutme.service.AlarmService;

import com.example.aboutme.domain.Alarm;

import java.util.List;

public interface AlarmService {

    List<Alarm> getProfileAlarm(Long memberId);
}
