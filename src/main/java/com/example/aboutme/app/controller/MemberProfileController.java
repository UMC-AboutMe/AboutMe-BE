package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.service.MemberProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
