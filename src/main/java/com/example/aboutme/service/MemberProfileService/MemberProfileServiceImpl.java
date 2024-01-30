package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.repository.MemberProfileRepository;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    // 프로필 보관함 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long memberId, Long profileId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Profile profile = profileRepository.findById(profileId).get();

        MemberProfile memberProfile = memberProfileRepository.findByMemberAndProfile(member, profile);
        if (memberProfile == null) {
            throw new GeneralException(ErrorStatus.MEMBER_PROFILE_NOT_FOUND);
        }

        memberProfile.toggleFavorite();

        return memberProfile.getFavorite();
    }

}
