package com.example.aboutme.service.SpaceService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.SpaceRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final MemberService memberService;
    @Override
    @Transactional
    public Space JoinSpace(SpaceRequest.JoinDTO request) {
        Space newSpace = SpaceConverter.toSpace(request);
        //TODO 멤버가 스페이스 가지고 있는지 중복 검사 필요
        return spaceRepository.save(newSpace);
    }

    @Override
    @Transactional
    public Space readSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        // 존재하는지 검사
        if (!spaceRepository.existsByMember_Id(memberId)) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        return spaceRepository.findByMember_Id(memberId);
    }

    @Override
    @Transactional
    public void deleteSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        Space targetSpace = readSpace(memberId);
        spaceRepository.delete(targetSpace);
    }
}
