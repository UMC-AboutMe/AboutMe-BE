package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberProfileService {
    Boolean toggleFavorite(Long memberId, Long profileId);

    void AddOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request);
}
