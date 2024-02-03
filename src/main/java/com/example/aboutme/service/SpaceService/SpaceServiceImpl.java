package com.example.aboutme.service.SpaceService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.converter.PlanConverter;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.constant.Mood;
import com.example.aboutme.repository.SpaceRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final MemberService memberService;

    /**
     * 내 마이프로필 생성
     * @param request
     * @return 생성된 마이스페이스의 특징
     */
    @Transactional
    public Space JoinSpace(SpaceRequest.JoinDTO request) {
        Space newSpace = SpaceConverter.toSpace(request);
        return spaceRepository.save(newSpace);
    }

    /**
     * 내 마이프로필 조회
     * @param memberId 멤버 식별자
     * @return 조회된 마이스페이스의 특징
     */
    public Space readSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        return spaceRepository.findByMember_Id(memberId);
    }

    /**
     * 내 마이프로필 삭제
     * @param memberId 멤버 식별자
     */
    @Transactional
    public void deleteSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        if (!spaceRepository.existsByMember(member)) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        Space targetSpace = spaceRepository.findByMember_Id(memberId);
        spaceRepository.delete(targetSpace);
    }

    /**
     * 내 마이프로필 수정
     * @param memberId 멤버 식별자
     * @param request
     * @return 수정된 마이스페이스의 특징
     */
    @Transactional
    public Space updateResult(Long memberId, SpaceRequest.UpdateDTO request) {
        Member member = memberService.findMember(memberId);
        Optional<Space> optionalSpace = spaceRepository.findByMember(member);

        if (!optionalSpace.isPresent()) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }

        Space targetSpace = optionalSpace.get();

        if (request.getNickname() != null) targetSpace.updateNickname(request.getNickname());
        if (request.getCharacterType() != null) targetSpace.updateCharacterType(request.getCharacterType());
        if (request.getRoomType() != null) targetSpace.updateRoomType(request.getRoomType());
        if (request.getMood() != null) targetSpace.updateMood(Mood.valueOf(request.getMood()));
        if (request.getMusicUrl() != null) targetSpace.updateMusirUrl(request.getMusicUrl());
        if (request.getStatusMessage() != null) targetSpace.updateStatusMessage(request.getStatusMessage());

        return targetSpace;
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
