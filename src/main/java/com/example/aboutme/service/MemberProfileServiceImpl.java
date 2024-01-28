package com.example.aboutme.service;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.repository.MemberProfileRepository;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileServiceImpl implements MemberProfileService{

    private final MemberProfileRepository memberProfileRepository;
    private final MemberService memberService;

    public List<MemberProfile> getMyProfilesStorage(Long memberId){
        Member member = memberService.findMember(memberId);

        return memberProfileRepository.findAllByMember(member);
    }

    @Transactional
    public MemberProfile deleteMemberProfile(Long memberId, Long profileId){
        Member member = memberService.findMember(memberId);

        MemberProfile memberProfile = memberProfileRepository.findByMemberAndId(member, profileId);
        memberProfileRepository.delete(memberProfile);
        return memberProfile;
    }
}
