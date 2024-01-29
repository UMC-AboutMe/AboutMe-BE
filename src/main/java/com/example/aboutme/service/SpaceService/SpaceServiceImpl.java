package com.example.aboutme.service.SpaceService;

import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.SpaceRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        return spaceRepository.findByMember_Id(memberId);
    }

    @Override
    public Space deleteSpace(Long memberId) {
        Member member = memberService.findMember(memberId);
        Space targetSpace = spaceRepository.findByMember_Id(memberId);
        spaceRepository.delete(targetSpace);
        return targetSpace;
    }
}
