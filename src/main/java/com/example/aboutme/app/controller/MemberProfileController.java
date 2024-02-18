package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.ApiResponse;

import com.example.aboutme.app.dto.MemberProfileRequest;
import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.converter.MemberSpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.service.MemberService.MemberService;
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
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @GetMapping()
    public ApiResponse<MemberProfileResponse.SearchMemberProfileListDTO> getMyProfilesStorage(@RequestHeader("token") String token) {
        String email = tokenProvider.getEmailFromToken(token);
        List<MemberProfile> memberProfileList = memberProfileService.getMyProfilesStorage(email);
        return ApiResponse.onSuccess(MemberProfileConverter.toSearchMemberProfileListDTO(memberProfileList));
    }

    @DeleteMapping("/{profileId}")
    public ApiResponse<MemberProfileResponse.DeleteMemberProfileMsgDTO> deleteMemberProfile(@RequestHeader("token") String token, @PathVariable Long profileId) {
        String email = tokenProvider.getEmailFromToken(token);
        MemberProfile memberProfile = memberProfileService.deleteMemberProfile(email, profileId);
        return ApiResponse.onSuccess(MemberProfileConverter.toDeleteMemberProfileMsgDTO(memberProfile.getId(),"success"));
    }

    @PatchMapping("/{profileId}/favorite")
    public ApiResponse<MemberProfileResponse.favoriteDto> toggleFavorite(@RequestHeader("token") String token,
                                                                         @PathVariable @ExistMyProfile Long profileId) {
        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        Boolean favoriteStatus = memberProfileService.toggleFavorite(member.getId(), profileId);

        return ApiResponse.onSuccess(MemberProfileConverter.toToggleFavorite(favoriteStatus));
    }

    @GetMapping("/search")
    public ApiResponse<MemberProfileResponse.SearchMemberProfileListDTO> searchMemberProfileList(@RequestHeader("token") String token,
                                                                                           @RequestParam(defaultValue = "") String keyword) {
        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        List<MemberProfile> memberProfileList = memberProfileService.filterWithKeyword(member.getId(), keyword);
        log.info("프로필 보관함 내 검색하기: member={}, keyword={}", member.getId(), keyword);

        return ApiResponse.onSuccess(MemberProfileConverter.toSearchMemberProfileListDTO(memberProfileList));
    }
}
