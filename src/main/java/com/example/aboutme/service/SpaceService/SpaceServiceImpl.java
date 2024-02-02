package com.example.aboutme.service.SpaceService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.converter.PlanConverter;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.SpaceRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final MemberService memberService;

    @Transactional
    public Space JoinSpace(SpaceRequest.JoinDTO request) {
        Space newSpace = SpaceConverter.toSpace(request);
        return spaceRepository.save(newSpace);
    }


    public Space readSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        return spaceRepository.findByMember_Id(memberId);
    }


    @Transactional
    public void deleteSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        if (!spaceRepository.existsByMember(member)) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        Space targetSpace = spaceRepository.findByMember_Id(memberId);
        spaceRepository.delete(targetSpace);
    }

    @Override
    @Transactional
    public Space createPlan(Long memberId, PlanRequest.CreatePlanDTO request) throws ParseException {
        Member member = memberService.findMember(memberId); // 멤버 검사
        if (!spaceRepository.existsByMember(member)) { // 스페이스 검사
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        Space space = spaceRepository.findByMember(member).get();
        space.addPlan(PlanConverter.toPlan(space, request));
        return space;
    }
}
