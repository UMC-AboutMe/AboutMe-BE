package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.converter.MemberSpaceConverter;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.service.MemberSpaceService.MemberSpaceService;
import com.example.aboutme.service.MemberSpaceService.MemberSpaceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myspaces/storage")
@RequiredArgsConstructor
public class MemberSpaceController {

    private final MemberSpaceService memberSpaceService;

    // 아지트 내 스페이스 목록 조회
    @GetMapping
    public ApiResponse<MemberSpaceResponse.GetListDto> getList(@RequestHeader("member-id") Long memberId,
                                                               @RequestParam(defaultValue = "") String keyword) {
        List<MemberSpace> memberSpaceList = memberSpaceService.filterWithKeyword(memberId, keyword);
        return ApiResponse.onSuccess(MemberSpaceConverter.toGetMemberSpaceListDTO(memberSpaceList));
    }

    // 아지트 내 스페이스 즐겨찾기
    @PatchMapping("/{spaceId}/favorite")
    public ApiResponse<MemberSpaceResponse.favoriteDto> toggleFavorite(@RequestHeader("member-id") Long memberId,
                                                                       @PathVariable Long spaceId) {
        Boolean favoriteStatus = memberSpaceService.toggleFavorite(memberId, spaceId);
        return ApiResponse.onSuccess(MemberSpaceConverter.toToggleFavorite(favoriteStatus));
    }

    // 아지트 내 스페이스 추가
    @PostMapping("/{spaceId}")
    public ApiResponse<MemberSpaceResponse.addDto> add(@RequestHeader("member-id") Long memberId,
                                                       @PathVariable Long spaceId) {
        MemberSpace newMemberSpace = memberSpaceService.addMemberSpace(memberId, spaceId);
        return ApiResponse.onSuccess(MemberSpaceConverter.toAddMemberSpaceDTO(newMemberSpace));
    }

    // 아지트 내 스페이스 삭제
    @DeleteMapping("/{spaceId}")
    public ApiResponse<Void> delete(@RequestHeader("member-id") Long memberId,
                                    @PathVariable Long spaceId) {
        memberSpaceService.deleteMemberSpace(memberId, spaceId);
        return ApiResponse.onSuccess(null);
    }
}
