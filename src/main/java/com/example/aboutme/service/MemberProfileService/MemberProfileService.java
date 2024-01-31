package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.app.dto.ProfileRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberProfileService {
    Boolean toggleFavorite(Long memberId, Long profileId);

    List<MemberProfile> getMyProfilesStorage(Long memberId);

    MemberProfile deleteMemberProfile(Long memberId, Long profileId);
    /**
     * 상대방 마이프로필 내 보관함에 추가하기
     * @param memberId 멤버 식별자
     * @param request
     */
    void addOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request);
}
