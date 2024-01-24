package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.service.ProfileService.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myprofiles")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    /**
     * [GET] /myprofiles
     * 내 마이프로필 조회
     * @param memberId 멤버 식별자
     * @return
     */
    @GetMapping()
    public ApiResponse<ProfileResponse.GetProfileListDTO> getMyProfiles(@RequestHeader("member_id") Long memberId){

        List<Profile> profileList = profileService.getMyProfiles(memberId);

        return ApiResponse.onSuccess(ProfileConverter.toGetProfileListDTO(profileList));
    }

    /**
     * [POST] /myprofiles
     * 마이프로필 생성
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @PostMapping()
    public ApiResponse createMyProfile(@RequestHeader("member_id") Long memberId, @RequestBody @Valid ProfileRequest.CreateProfileDTO request){

        Profile newProfile = profileService.createMyProfile(memberId, request);

        log.info("마이프로필 생성: {}", request.getName());

        return ApiResponse.onSuccess(ProfileConverter.toCreateProfileDTO(newProfile));
    }
}
