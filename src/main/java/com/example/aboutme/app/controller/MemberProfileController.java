package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.service.MemberProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/myprofiles/storage")
@RequiredArgsConstructor
@Slf4j
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @GetMapping()
    public ApiResponse getMyProfilesStorage(@RequestHeader("member_id") Long memberId) {
        List<MemberProfile> memberProfileList = memberProfileService.getMyProfilesStorage(memberId);

        return ApiResponse.onSuccess(MemberProfileConverter.toGetMemberProfileListDTO(memberProfileList));
    }

    @DeleteMapping("/{profile_id}")
    public ApiResponse deleteMemberProfile(@RequestHeader("member_id") Long memberId, @RequestBody @Valid Long profile_id){
        MemberProfile memberProfile = memberProfileService.deleteMemberProfile(memberId, profile_id);
        return ApiResponse.onSuccess(MemberProfileConverter.toMemberProfileDTO(memberProfile));
    }
}
