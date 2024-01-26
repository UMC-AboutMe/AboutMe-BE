package com.example.aboutme.Login.controller;

import com.example.aboutme.Login.common.MsgEntity;
import com.example.aboutme.Login.dto.GoogleDTO;
import com.example.aboutme.Login.dto.KakaoDTO;
import com.example.aboutme.Login.service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("google")
public class GoogleController {
    private final GoogleService googleService;

    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        GoogleDTO googleInfo = googleService.getGoogleInfo(request.getParameter("code"));
        googleService.saveGoogleMember(googleInfo);
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", googleInfo));
    }
}
