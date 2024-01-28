package com.example.aboutme.service;

import com.example.aboutme.domain.mapping.MemberProfile;

import java.util.List;

public interface MemberProfileService {
    List<MemberProfile> getMyProfilesStorage(Long memberId);

    MemberProfile deleteMemberProfile(Long memberId, Long profileId);
}
