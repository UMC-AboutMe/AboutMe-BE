package com.example.aboutme.Login.controller;

import com.example.aboutme.Login.common.MsgEntity;
import com.example.aboutme.Login.dto.KakaoDTO;
import com.example.aboutme.Login.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
        kakaoService.saveKakaoMember(kakaoInfo);
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", kakaoInfo));
    }


}
