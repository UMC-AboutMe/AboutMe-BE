package com.example.aboutme.service.AlarmService;

import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
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

    @Override
    @Transactional
    public Alarm shareSpace(Long memberId) {
        // 멤버 조회
        Member member = memberService.findMember(memberId);
        // 스페이스 조회
        Space space = spaceService.findSpace(member);
        Alarm alarm = AlarmConverter.toAlarm(member, space.getId());
        return alarm;
    }
}
