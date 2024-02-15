package com.example.aboutme.service.MemberService;

import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMember(Long memberId);
    void deleteMember(Long memberId);

    /**
     * 마이페이지 조회
     * @param memberId 멤버 식별자
     * @return 마이프로필 정보
     */
    MyPageResponse.GetMyPageDTO getMyPage(Long memberId);
}
