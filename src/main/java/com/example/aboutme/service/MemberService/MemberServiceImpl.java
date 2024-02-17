package com.example.aboutme.service.MemberService;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.converter.MemberConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProfileFeatureRepository profileFeatureRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberSpaceRepository memberSpaceRepository;

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }

    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }

    @Override
    public Member findMember(String email, Social social) {
        return memberRepository.findByEmailAndSocial(email, social).orElseThrow(
                () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }
    public Member findMember(TokenDTO.tokenClaimsDTO tokenClaimsDTO){
        log.info("이메일={}, 소셜={}", tokenClaimsDTO.getEmail(), tokenClaimsDTO.getSocial());
        return memberRepository.findByEmailAndSocial(tokenClaimsDTO.getEmail(), tokenClaimsDTO.getSocial())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        findMember(memberId);
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }

    /**
     * 마이페이지 조회
     * @param tokenClaimsDTO 멤버 식별자
     * @return 마이프로필 정보
     */
    public MyPageResponse.GetMyPageDTO getMyPage(TokenDTO.tokenClaimsDTO tokenClaimsDTO){
        Member member = findMember(tokenClaimsDTO);

        String profileName = profileFeatureRepository.findProfileFeature(member, PageRequest.of(0,1)).get(0);
        String spaceName = member.getSpace() != null ? member.getSpace().getNickname() : null;
        int profileSharedNum = memberProfileRepository.countSharedProfileByMember(member);
        int spaceSharedNum = memberSpaceRepository.countSharedSpaceByMember(member);
        return MemberConverter.toGetMyPageDTO(profileName, spaceName, profileSharedNum, spaceSharedNum);
    }
}
