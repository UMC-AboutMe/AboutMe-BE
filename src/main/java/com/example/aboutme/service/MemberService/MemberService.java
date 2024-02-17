package com.example.aboutme.service.MemberService;

import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMember(Long memberId);
    Member findMember(String email);
    void deleteMember(Long memberId);

    void deleteMember(String email);

    /**
     * 마이페이지 조회
     * @param memberId 멤버 식별자
     * @return 마이프로필 정보
     */
    MyPageResponse.GetMyPageDTO getMyPage(Long memberId);
}
