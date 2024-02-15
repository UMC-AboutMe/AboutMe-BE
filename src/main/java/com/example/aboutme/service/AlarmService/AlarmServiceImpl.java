package com.example.aboutme.service.AlarmService;

import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.repository.AlarmRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService {

    private final MemberService memberService;
    private final AlarmRepository alarmRepository;

    /**
     * 프로필 알람 데이터 조회
     * @param memberId 멤버 식별자
     */
    public List<Alarm> getProfileAlarm(Long memberId) {

        Member member = memberService.findMember(memberId);

        return alarmRepository.findByMember(member);
    }
}
