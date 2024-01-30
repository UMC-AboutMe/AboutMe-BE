package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;

import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/myprofiles/storage")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @GetMapping()
    public ApiResponse getMyProfilesStorage(@RequestHeader("member_id") Long memberId) {
        List<MemberProfile> memberProfileList = memberProfileService.getMyProfilesStorage(memberId);

        return ApiResponse.onSuccess(MemberProfileConverter.toGetMemberProfileListDTO(memberProfileList));
    }

    // 프로필 보관함 즐겨찾기
    @PatchMapping("/{profileId}/favorite")
    public ApiResponse<MemberProfileResponse.favoriteDto> toggleFavorite(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable @ExistMyProfile Long profileId) {
        Boolean favoriteStatus = memberProfileService.toggleFavorite(memberId, profileId);

        return ApiResponse.onSuccess(MemberProfileConverter.toToggleFavorite(favoriteStatus));
    }
}
