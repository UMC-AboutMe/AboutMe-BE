package com.example.aboutme.service.AlarmService;
import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.AlarmRequest;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.AlarmRepository;
import com.example.aboutme.service.MemberService.MemberService;
import com.example.aboutme.service.SpaceService.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
        Member toMember = memberService.findMember(request.getToMemberId());
        Alarm alarm = AlarmConverter.toAlarm(toMember, space, space.getNickname());

        return alarmRepository.save(alarm);
    }

    /**
     * 프로필 알람 데이터 조회
     * @param memberId 멤버 식별자
     */
    public List<Alarm> getAlarmList(Long memberId) {

        Member member = memberService.findMember(memberId);

        return alarmRepository.findByMember(member);
    }

    /**
     * 프로필 알람 데이터 삭제
     * @param member 멤버
     * @param alarmId 알림 식별자
     */
    @Transactional
    public void deleteAlarm(Member member, Long alarmId) {

        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new GeneralException(ErrorStatus.ALARM_NOT_FOUND)
        );

        if (!alarm.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.ALARM_NOT_MINE);
        }

        log.info("프로필 알람 데이터 삭제: alarmId={}", alarmId);

        alarmRepository.delete(alarm);
    }
}
