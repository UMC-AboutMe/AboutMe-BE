package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.Login.jwt.TokenProvider;
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
    private final TokenProvider tokenProvider;

    /**
     * [GET] /mypages
     * @param accessToken 멤버 식별자
     * @return
     */
    @GetMapping("")
    public ApiResponse<MyPageResponse.GetMyPageDTO> getMyPage(@RequestHeader("token") String accessToken){

        TokenDTO.tokenClaimsDTO tokenClaimsDTO = tokenProvider.getTokenInfoFromToken(accessToken);

        MyPageResponse.GetMyPageDTO getMyPageDTO = memberService.getMyPage(tokenClaimsDTO);

        log.info("마이페이지 조회: {}", tokenClaimsDTO.getEmail());

        return ApiResponse.onSuccess(getMyPageDTO);
    }
}
