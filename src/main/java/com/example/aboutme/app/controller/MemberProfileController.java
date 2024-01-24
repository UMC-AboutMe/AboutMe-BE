package com.example.aboutme.app.controller;

import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.service.MemberProfileService;
import com.example.aboutme.service.MemberSpaceService;
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
    public ResponseEntity<Map<String, Object>> toggleFavorite(@PathVariable Long profileId) {
        Boolean favoriteStatus = memberProfileService.toggleFavorite(profileId);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("favorite", favoriteStatus);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
