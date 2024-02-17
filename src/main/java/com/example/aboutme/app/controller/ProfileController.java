package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.app.dto.ProfileResponse;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.ProfileImage;
import com.example.aboutme.domain.constant.ProfileImageType;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.service.ProfileService.ProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import com.example.aboutme.validation.annotation.ExistProfileBySerialNum;
import com.example.aboutme.validation.annotation.ExistProfilesBySerialNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final TokenProvider tokenProvider;

    /**
     * [GET] /myprofiles
     * 내 마이프로필 목록 조회
     *
     * @param accessToken 멤버 식별자
     * @return
     */
    @GetMapping()
    public ApiResponse<ProfileResponse.GetProfileListDTO> getMyProfiles(@RequestHeader("token") String accessToken) {
        TokenDTO.tokenClaimsDTO tokenClaimsDTO = tokenProvider.getTokenInfoFromToken(accessToken);

        List<Profile> profileList = profileService.getMyProfiles(tokenClaimsDTO);

        log.info("마이프로필 조회: member={}", tokenClaimsDTO.getEmail());

        return ApiResponse.onSuccess(ProfileConverter.toGetProfileListDTO(profileList));
    }

    /**
     * [GET] /myprofiles/{profile-id}
     * 마이프로필 단건 조회
     *
     * @param profileId 마이프로필 식별자
     * @return
     */
    @GetMapping("/{profile-id}")
    public ApiResponse<ProfileResponse.GetMyProfileDTO> getMyProfile(@PathVariable("profile-id") @ExistMyProfile Long profileId) {

        Profile profile = profileService.getMyProfile(profileId);

        log.info("마이프로필 조회(단건):git profileID={}", profileId);

        return ApiResponse.onSuccess(ProfileConverter.toGetMyProfileDTO(profile));
    }

    /**
     * [POST] /myprofiles
     * 마이프로필 생성
     *
     * @param accessToken
     * @param request
     * @return
     */
    @PostMapping()
    public ApiResponse createMyProfile(@RequestHeader("token") String accessToken, @RequestBody @Valid ProfileRequest.CreateProfileDTO request) {
        TokenDTO.tokenClaimsDTO tokenClaimsDTO = tokenProvider.getTokenInfoFromToken(accessToken);

        Profile newProfile = profileService.createMyProfile(tokenClaimsDTO, request);

        log.info("마이프로필 생성: {}", request.getName());

        return ApiResponse.onSuccess(ProfileConverter.toCreateProfileDTO(newProfile));
    }

    @PatchMapping("/default/{profileId}")
    public ApiResponse<ProfileResponse.UpdateDefaultProfileDTO> patchDefaultMyProfileToTrue(@RequestHeader("token") String token, @PathVariable Long profileId) {
        String email = tokenProvider.getEmailFromToken(token);
        Profile updatedProfile = profileService.updateIsDefault(email, profileId);
        return ApiResponse.onSuccess(ProfileConverter.toUpdateDefaultProfile(updatedProfile));
    }

    @PatchMapping("/defaultToFalse/{profileId}")
    public ApiResponse<ProfileResponse.UpdateDefaultProfileDTO> patchDefaultMyProfileToFalse(@RequestHeader("token") String token, @PathVariable Long profileId) {
        String email = tokenProvider.getEmailFromToken(token);
        Profile updatedProfile = profileService.updateIsDefaultToFalse(email,profileId);
        return ApiResponse.onSuccess(ProfileConverter.toUpdateDefaultProfile(updatedProfile));
    }

    /**
     * [PATCH] /myprofiles/{profile-id}
     * 내 마이프로필 수정
     *
     * @param memberId  멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return
     */
    @PatchMapping("/{profile-id}")
    public ApiResponse<ProfileResponse.UpdateProfileDTO> updateMyProfile(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable("profile-id") @ExistMyProfile Long profileId,
                                                                         @RequestBody @Valid ProfileRequest.UpdateProfileDTO request) {

        ProfileFeature profileFeature = profileService.updateMyProfile(memberId, profileId, request);

        log.info("프로필 값: request={}, response={}", request.getFeatureValue(), profileFeature.getProfileValue());

        log.info("마이프로필 수정: {}", request.getFeatureId());

        return ApiResponse.onSuccess(ProfileConverter.toUpdateProfileDTO(profileFeature));
    }

    /**
     * [PATCH] /myprofiles/{profile-id}/image
     * 내 마이프로필 이미지 수정
     *
     * @param memberId  멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param image     이미지
     * @param request
     * @return
     */
    @PatchMapping("/{profile-id}/image")
    public ApiResponse<ProfileResponse.UpdateMyProfileImageDTO> updateMyProfileImage(@RequestHeader("member-id") Long memberId,
                                                                                     @PathVariable("profile-id") @ExistMyProfile Long profileId,
                                                                                     @RequestPart(value = "image", required = false) MultipartFile image,
                                                                                     @RequestPart(value = "body", required = true) @Valid ProfileRequest.UpdateProfileImageDTO request) {


        ProfileImageType profileImageType = ProfileImageType.valueOf(request.getProfileImageType());
        boolean isProfileImageEmpty = (profileImageType == ProfileImageType.USER_IMAGE) && (image == null);
        if (isProfileImageEmpty) {
            throw new GeneralException(ErrorStatus.PROFILE_IMAGE_REQUIRED);
        }

        ProfileImage updatedProfileImage = profileService.updateMyProfileImage(memberId, profileId, image, request);

        log.info("내 마이프로필 이미지 수정: 타입={}, 프로필={}", request.getProfileImageType(), profileId);

        return ApiResponse.onSuccess(ProfileConverter.toUpdateMyProfileImageDTO(updatedProfileImage));
    }

    /**
     * [DELETE] /myprofiles/{profile-id}
     * 내 마이프로필 삭제
     *
     * @param memberId  멤버 식별자
     * @param profileId 마이프로필 식별자
     * @return
     */
    @DeleteMapping("/{profile-id}")
    public ApiResponse deleteMyProfile(@RequestHeader("member-id") Long memberId, @PathVariable("profile-id") @ExistMyProfile Long profileId) {
        profileService.deleteMyProfile(memberId, profileId);

        log.info("마이프로필 삭제: {}", profileId);

        return ApiResponse.onSuccess(null);
    }

    /**
     * [POST] /myprofiles/share
     * 상대방 마이프로필 내 보관함에 추가하기
     *
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @PostMapping("/share")
    public ApiResponse<Void> shareProfile(@RequestHeader("member-id") Long memberId,
                                                                     @RequestBody @Valid ProfileRequest.ShareProfileDTO request) {

        memberProfileService.addOthersProfilesAtMyStorage(memberId, request);

        log.info("상대방 마이프로필 내 보관함에 추가하기: member={}, other's profile={}", memberId, request.getProfileSerialNumberList());

        return ApiResponse.onSuccess(null);
    }

    /**
     * [POST] /myprofiles/send
     * 마이프로필 공유 → 알림 데이터 생성
     *
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @PostMapping("/send")
    public ApiResponse<Void> sendProfile(@RequestHeader("member-id") Long memberId,
                                          @RequestBody @Valid ProfileRequest.SendProfileDTO request) {

        memberProfileService.sendMyProfile(memberId, request);

        log.info("마이프로필 공유 → 알림 데이터 생성: member={}, other's profile={}, my profile={}", memberId, request.getOthersProfileSerialNumberList(), request.getMyProfileSerialNumberList());

        return ApiResponse.onSuccess(null);
    }

    /**
     * [GET] /myprofiles/search?q=
     * 프로필 검색
     * @param serialNumber 시리얼 넘버
     * @return
     */
    @GetMapping("/search")
    public ApiResponse<ProfileResponse.SearchProfileDTO> searchProfile(@RequestParam(value = "q") @ExistProfileBySerialNum int serialNumber){

        Profile profile = profileService.searchProfile(serialNumber);

        log.info("마이프로필 검색하기: {}", serialNumber);

        return ApiResponse.onSuccess(ProfileConverter.toSearchProfile(profile));
    }
}
