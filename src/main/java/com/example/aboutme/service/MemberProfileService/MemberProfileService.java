package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.domain.mapping.MemberProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberProfileService {
    Boolean toggleFavorite(Long memberId, Long profileId);

    List<MemberProfile> getMyProfilesStorage(Long memberId);

    MemberProfile deleteMemberProfile(Long memberId, int serialNumber);
}
