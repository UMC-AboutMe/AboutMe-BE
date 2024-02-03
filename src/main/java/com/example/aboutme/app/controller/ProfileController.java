package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.service.ProfileService.ProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/myprofiles")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final MemberProfileService memberProfileService;

    /**
     * [GET] /myprofiles
     * 내 마이프로필 조회
     *
     * 내 마이프로필 목록
     * @param memberId 멤버 식별자
     * @return
     */
    @GetMapping()
    public ApiResponse<ProfileResponse.GetProfileListDTO> getMyProfiles(@RequestHeader("member-id") Long memberId) {

        List<Profile> profileList = profileService.getMyProfiles(memberId);

        log.info("마이프로필 조회: member={}", memberId);

        return ApiResponse.onSuccess(ProfileConverter.toGetProfileListDTO(profileList));
    }

    /**
     * [GET] /myprofiles/{profile-id}
     * 내 마이프로필 단건 조회
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @return
     */
    @GetMapping("/{profile-id}")
    public ApiResponse<ProfileResponse.GetMyProfileDTO> getMyProfile(@RequestHeader("member-id") Long memberId,
                                                                     @PathVariable("profile-id") @ExistMyProfile Long profileId){

        Profile profile = profileService.getMyProfile(memberId, profileId);

        log.info("마이프로필 조회(단건): profileID={}", profileId);

        return ApiResponse.onSuccess(ProfileConverter.toGetMyProfileDTO(profile));
    }

    /**
     * [POST] /myprofiles
     * 마이프로필 생성
     *
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @PostMapping()
    public ApiResponse createMyProfile(@RequestHeader("member-id") Long memberId, @RequestBody @Valid ProfileRequest.CreateProfileDTO request) {

        Profile newProfile = profileService.createMyProfile(memberId, request);

        log.info("마이프로필 생성: {}", request.getName());

        return ApiResponse.onSuccess(ProfileConverter.toCreateProfileDTO(newProfile));
    }

    @PatchMapping("/default/{profileId}")
    public ApiResponse<ProfileResponse.UpdateDefaultProfileDTO> patchDefaultMyProfile(@RequestHeader("member-id") Long memberId, @PathVariable Long profileId) {
        Profile updatedProfile = profileService.updateIsDefault(memberId, profileId);
        return ApiResponse.onSuccess(ProfileConverter.toUpdateDefaultProfile(updatedProfile));
    }

    /**
     * [PATCH] /myprofiles/{profile-id}
     * 내 마이프로필 수정
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return
     */
    @PatchMapping("/{profile-id}")
    public ApiResponse<ProfileResponse.UpdateProfileDTO> updateMyProfile(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable("profile-id") @ExistMyProfile Long profileId,
                                                                         @RequestBody @Valid ProfileRequest.UpdateProfileDTO request){

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

    /**
     * [POST] /myprofiles/share
     * 상대방 마이프로필 내 보관함에 추가하기
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @PostMapping("/share")
    public ApiResponse<Void> shareProfile(@RequestHeader("member-id") Long memberId,
                                          @RequestBody @Valid ProfileRequest.ShareProfileDTO request){

        memberProfileService.addOthersProfilesAtMyStorage(memberId, request);

        log.info("상대방 마이프로필 내 보관함에 추가하기: member={}, other's profile={}", memberId, request.getProfileSerialNumberList());

        return ApiResponse.onSuccess(null);
    }
}
