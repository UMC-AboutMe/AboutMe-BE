package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.service.ProfileService.ProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
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
    public ApiResponse<ProfileResponse.GetProfileListDTO> getMyProfiles(@RequestHeader("member-id") Long memberId){

        List<Profile> profileList = profileService.getMyProfiles(memberId);

        log.info("마이프로필 조회: member={}", memberId);

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
    public ApiResponse createMyProfile(@RequestHeader("member-id") Long memberId, @RequestBody @Valid ProfileRequest.CreateProfileDTO request){

        Profile newProfile = profileService.createMyProfile(memberId, request);

        log.info("마이프로필 생성: {}", request.getName());

        return ApiResponse.onSuccess(ProfileConverter.toCreateProfileDTO(newProfile));
    }

    /**
     * 내 마이프로필 수정
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return 
     */
    @PatchMapping("/{profile-id}")
    public ApiResponse<ProfileResponse.UpdateProfileDTO> updateMyProfile(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable("profile-id") Long profileId,
                                                                         @RequestBody ProfileRequest.UpdateProfileDTO request){

        ProfileFeature profileFeature = profileService.updateMyProfile(memberId, profileId, request);

        log.info("프로필 값: request={}, response={}", request.getFeatureValue(), profileFeature.getProfileValue());

        log.info("마이프로필 수정: {}", request.getFeatureId());

        return ApiResponse.onSuccess(ProfileConverter.toUpdateProfileDTO(profileFeature));
    }

    /**
     * [DELETE] /myprofiles/{profile-id}
     * 내 마이프로필 삭제
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @return
     */
    @DeleteMapping("/{profile-id}")
    public ApiResponse deleteMyProfile(@RequestHeader("member-id") Long memberId, @PathVariable("profile-id") @ExistMyProfile Long profileId){
        profileService.deleteMyProfile(memberId, profileId);

        log.info("마이프로필 삭제: {}", profileId);

        return ApiResponse.onSuccess(null);
    }
}
