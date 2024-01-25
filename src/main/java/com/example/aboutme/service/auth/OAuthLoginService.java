package com.example.aboutme.service.auth;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail()).
                map(Member::getId)
                .orElseGet(()-> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder().email(oAuthInfoResponse.getEmail())
                .Social(oAuthInfoResponse.getOAuthProvider()).build();

        return memberRepository.save(member).getId();
    }
}
