package com.example.aboutme.service.AlarmService;
import com.example.aboutme.app.dto.AlarmRequest;
import com.example.aboutme.domain.Alarm;

import java.util.List;

public interface AlarmService {

    Alarm shareSpace(Long memberId, AlarmRequest.CreateDTO request);
    List<Alarm> getAlarmList(Long memberId);
}
