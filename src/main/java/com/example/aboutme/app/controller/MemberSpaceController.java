package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.converter.MemberSpaceConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.service.MemberService.MemberService;
import com.example.aboutme.service.MemberSpaceService.MemberSpaceService;
import com.example.aboutme.service.MemberSpaceService.MemberSpaceServiceImpl;
import com.example.aboutme.validation.annotation.ExistMySpace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myspaces/storage")
@RequiredArgsConstructor
@Validated
public class MemberSpaceController {

    private final MemberSpaceService memberSpaceService;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    // 아지트 내 스페이스 목록 조회
    @GetMapping
    public ApiResponse<MemberSpaceResponse.GetListDto> getList(@RequestHeader("token") String token,
                                                               @RequestParam(defaultValue = "") String keyword) {

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        List<MemberSpace> memberSpaceList = memberSpaceService.filterWithKeyword(member.getId(), keyword);
        return ApiResponse.onSuccess(MemberSpaceConverter.toGetMemberSpaceListDTO(memberSpaceList));
    }

    // 아지트 내 스페이스 즐겨찾기
    @PatchMapping("/{spaceId}/favorite")
    public ApiResponse<MemberSpaceResponse.favoriteDto> toggleFavorite(@RequestHeader("token") String token,
                                                                       @PathVariable @ExistMySpace Long spaceId) {

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        Boolean favoriteStatus = memberSpaceService.toggleFavorite(member.getId(), spaceId);
        return ApiResponse.onSuccess(MemberSpaceConverter.toToggleFavorite(favoriteStatus));
    }

    // 아지트 내 스페이스 추가
    @PostMapping("/{spaceId}")
    public ApiResponse<MemberSpaceResponse.addDto> add(@RequestHeader("token") String token,
                                                       @PathVariable @ExistMySpace Long spaceId) {

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        MemberSpace newMemberSpace = memberSpaceService.addMemberSpace(member.getId(), spaceId);
        return ApiResponse.onSuccess(MemberSpaceConverter.toAddMemberSpaceDTO(newMemberSpace));
    }

    // 아지트 내 스페이스 삭제
    @DeleteMapping("/{spaceId}")
    public ApiResponse<Void> delete(@RequestHeader("token") String token,
                                    @PathVariable @ExistMySpace Long spaceId) {

        String email = tokenProvider.getTokenInfoFromToken(token).getEmail();
        Social social = tokenProvider.getTokenInfoFromToken(token).getSocial();
        Member member = memberService.findMember(email, social);

        memberSpaceService.deleteMemberSpace(member.getId(), spaceId);
        return ApiResponse.onSuccess(null);
    }
}
