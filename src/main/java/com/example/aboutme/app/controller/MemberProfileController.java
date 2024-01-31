package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;

import com.example.aboutme.app.dto.MemberProfileRequest;
import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/myprofiles/storage")
@RequiredArgsConstructor
@Validated
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @GetMapping()
    public ApiResponse<MemberProfileResponse.GetMemberProfileListDTO> getMyProfilesStorage(@RequestHeader("member-id") Long memberId) {
        List<MemberProfile> memberProfileList = memberProfileService.getMyProfilesStorage(memberId);

        return ApiResponse.onSuccess(MemberProfileConverter.toGetMemberProfileListDTO(memberProfileList));
    }

    @DeleteMapping("/{profileId}")
    public ApiResponse<MemberProfileResponse.MemberProfileDTO> deleteMemberProfile(@RequestHeader("member-id") Long memberId, @RequestBody @Valid MemberProfileRequest.DeleteMemberDTO request) {
        MemberProfile memberProfile = memberProfileService.deleteMemberProfile(memberId, request.getProfileId());
        return ApiResponse.onSuccess(MemberProfileConverter.toMemberProfileDTO(memberProfile));
    }

    @PatchMapping("/{profileId}/favorite")
    public ApiResponse<MemberProfileResponse.favoriteDto> toggleFavorite(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable @ExistMyProfile Long profileId) {
        Boolean favoriteStatus = memberProfileService.toggleFavorite(memberId, profileId);

        return ApiResponse.onSuccess(MemberProfileConverter.toToggleFavorite(favoriteStatus));
    }
}
