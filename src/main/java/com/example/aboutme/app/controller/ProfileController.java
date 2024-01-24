package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.service.ProfileService.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myprofiles")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping()
    public ApiResponse createMyProfile(@RequestHeader("member_id") Long memberId, @RequestBody ProfileRequest.CreateProfileDTO request){

        Profile newProfile = profileService.createMyProfile(memberId, request);

        log.info("마이프로필 생성: {}", request.getName());

        return ApiResponse.onSuccess(ProfileConverter.toCreateProfileDTO(newProfile));
    }
}
