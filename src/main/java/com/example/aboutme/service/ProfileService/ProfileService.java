package com.example.aboutme.service.ProfileService;

import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.domain.Profile;

public interface ProfileService {

    /**
     * 마이프로필 생성
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    Profile createMyProfile(Long memberId, ProfileRequest.CreateProfileDTO request);

}
