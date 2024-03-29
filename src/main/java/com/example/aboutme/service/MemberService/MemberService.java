package com.example.aboutme.service.MemberService;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMember(Long memberId);
    Member findMember(String email);

    Member findMember(TokenDTO.tokenClaimsDTO tokenClaimsDTO);
    void deleteMember(Long memberId);

    Member findMember(String email, Social social);

    void deleteMember(String email);

    /**
     * 마이페이지 조회
     * @param tokenClaimsDTO 멤버 식별자
     * @return 마이프로필 정보
     */
    MyPageResponse.GetMyPageDTO getMyPage(TokenDTO.tokenClaimsDTO tokenClaimsDTO);
}
