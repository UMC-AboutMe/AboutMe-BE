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

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final MemberService memberService;
    @Override
    public Space JoinSpace(SpaceRequest.JoinDTO request) {
        Space newSpace = SpaceConverter.toSpace(request);
        return spaceRepository.save(newSpace);
    }

    @Override
    public Space readSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        // 존재하는지 검사
        if (!spaceRepository.findByMember_Id(memberId).isPresent()) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        return spaceRepository.findByMember_Id(memberId).get();
    }

    @Override
    public Space deleteSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        // 존재하는지 검사
        if (!spaceRepository.findByMember_Id(memberId).isPresent()) {
            throw new GeneralException(ErrorStatus.SPACE_NOT_FOUND);
        }
        Space targetSpace = spaceRepository.findByMember_Id(memberId).get();
        spaceRepository.delete(targetSpace);
        log.info("여기까지 성공");
        return targetSpace;
    }
}
