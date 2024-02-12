package com.example.aboutme.service.AlarmService;

import com.example.aboutme.app.dto.AlarmRequest;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.AlarmRepository;
import com.example.aboutme.service.MemberService.MemberService;
import com.example.aboutme.service.SpaceService.SpaceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AlarmServiceImpl implements AlarmService{
    private final MemberService memberService;
    private final SpaceService spaceService;
    private final AlarmRepository alarmRepository;

    @Override
    @Transactional
    public Alarm shareSpace(Long memberId, AlarmRequest.CreateDTO request) {
        // 공유하려는 멤버 조회
        Member fromMember = memberService.findMember(memberId);
        // 스페이스 조회
        Space space = spaceService.findSpace(fromMember);
        // 공유받는 멤버 조회
        Member toMember = memberService.findMember(request.getDestination());
        Alarm alarm = AlarmConverter.toAlarm(toMember, space.getNickname());

        return alarmRepository.save(alarm);
    }
}
