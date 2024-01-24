package com.example.aboutme.service;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.repository.MemberProfileRepository;
import com.example.aboutme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;

    // 프로필 보관함 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long profileId) {

        // 임시로 DB에 있는 1번 멤버 조회
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Member with id 1 not found"));

        MemberProfile memberProfile = memberProfileRepository.findByMemberAndProfileId(member, profileId);
        memberProfile.toggleFavorite();

        return memberProfile.getFavorite();
    }

}
