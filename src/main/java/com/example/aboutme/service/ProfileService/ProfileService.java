package com.example.aboutme.service.ProfileService;

import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.domain.Profile;

public interface ProfileService {

    Profile createMyProfile(Long memberId, ProfileRequest.CreateProfileDTO request);

}
