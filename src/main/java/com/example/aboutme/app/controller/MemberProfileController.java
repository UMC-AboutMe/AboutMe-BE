package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;

import com.example.aboutme.app.dto.MemberProfileRequest;
import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.converter.MemberSpaceConverter;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/myprofiles/storage")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @GetMapping()
    public ApiResponse<MemberProfileResponse.SearchMemberProfileListDTO> getMyProfilesStorage(@RequestHeader("member-id") Long memberId) {
        List<MemberProfile> memberProfileList = memberProfileService.getMyProfilesStorage(memberId);
        return ApiResponse.onSuccess(MemberProfileConverter.toSearchMemberProfileListDTO(memberProfileList));
    }

    @DeleteMapping("/{profileId}")
    public ApiResponse<MemberProfileResponse.DeleteMemberProfileMsgDTO> deleteMemberProfile(@RequestHeader("member-id") Long memberId, @PathVariable Long profileId) {
        MemberProfile memberProfile = memberProfileService.deleteMemberProfile(memberId, profileId);
        return ApiResponse.onSuccess(MemberProfileConverter.toDeleteMemberProfileMsgDTO(memberProfile.getId(),"success"));
    }

    @PatchMapping("/{profileId}/favorite")
    public ApiResponse<MemberProfileResponse.favoriteDto> toggleFavorite(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable @ExistMyProfile Long profileId) {
        Boolean favoriteStatus = memberProfileService.toggleFavorite(memberId, profileId);

        return ApiResponse.onSuccess(MemberProfileConverter.toToggleFavorite(favoriteStatus));
    }

    @GetMapping("/search")
    public ApiResponse<MemberProfileResponse.SearchMemberProfileListDTO> searchMemberProfileList(@RequestHeader("member-id") Long memberId,
                                                                                           @RequestParam(defaultValue = "") String keyword) {
        List<MemberProfile> memberProfileList = memberProfileService.filterWithKeyword(memberId, keyword);
        log.info("프로필 보관함 내 검색하기: member={}, keyword={}", memberId, keyword);

        return ApiResponse.onSuccess(MemberProfileConverter.toSearchMemberProfileListDTO(memberProfileList));
    }
}
