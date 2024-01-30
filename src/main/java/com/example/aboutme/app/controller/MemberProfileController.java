package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.MemberProfileResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.service.MemberProfileService.MemberProfileService;
import com.example.aboutme.service.MemberProfileService.MemberProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/myprofiles/storage")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    // 프로필 보관함 즐겨찾기
    @PatchMapping("/{profileId}/favorite")
    public ApiResponse<MemberProfileResponse.favoriteDto> toggleFavorite(@RequestHeader("member-id") Long memberId,
                                                                         @PathVariable Long profileId) {
        Boolean favoriteStatus = memberProfileService.toggleFavorite(memberId, profileId);

        return ApiResponse.onSuccess(MemberProfileConverter.toToggleFavorite(favoriteStatus));
    }
}
