package com.example.aboutme.service.MemberService;

import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMember(Long memberId);
    void deleteMember(Long memberId);

    MyPageResponse.GetMyPageDTO getMyPage(Long memberId);
}
