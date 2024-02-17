package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypages")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MemberService memberService;

    /**
     * [GET] /mypages
     * @param memberId 멤버 식별자
     * @return
     */
    @GetMapping("")
    public ApiResponse<MyPageResponse.GetMyPageDTO> getMyPage(@RequestHeader("member-id") Long memberId){

        MyPageResponse.GetMyPageDTO getMyPageDTO = memberService.getMyPage(memberId);

        log.info("마이페이지 조회: {}", memberId);

        return ApiResponse.onSuccess(getMyPageDTO);
    }
}
