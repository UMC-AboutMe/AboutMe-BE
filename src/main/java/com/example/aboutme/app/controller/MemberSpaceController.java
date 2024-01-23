package com.example.aboutme.app.controller;

import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.service.MemberSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/myspaces/storage")
@RequiredArgsConstructor
public class MemberSpaceController {

    private final MemberSpaceService memberSpaceService;

    // 아지트 내 스페이스 목록 조회
    @GetMapping
    public ResponseEntity<MemberSpaceResponse> getList(@RequestParam(defaultValue = "") String keyword) {

        MemberSpaceResponse response = memberSpaceService.filterWithKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 아지트 내 스페이스 즐겨찾기
    @PatchMapping("/{spaceId}/favorite")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@PathVariable Long spaceId) {
        Boolean favoriteStatus = memberSpaceService.toggleFavorite(spaceId);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("favorite", favoriteStatus);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 아지트 내 스페이스 추가
    @PostMapping("/{spaceId}")
    public ResponseEntity<Long> add(@PathVariable Long spaceId) {
        memberSpaceService.addMemberSpace(spaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(spaceId);
    }

    // 아지트 내 스페이스 삭제
    @DeleteMapping("/{spaceId}")
    public ResponseEntity<Long> delete(@PathVariable Long spaceId) {
        memberSpaceService.deleteMemberSpace(spaceId);
        return ResponseEntity.status(HttpStatus.OK).body(spaceId);
    }
}
