package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.app.dto.ProfileRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberProfileService {
    Boolean toggleFavorite(Long memberId, Long profileId);

    /**
     * 상대방 마이프로필 내 보관함에 추가하기
     * @param memberId 멤버 식별자
     * @param request
     */
    void addOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request);
}
