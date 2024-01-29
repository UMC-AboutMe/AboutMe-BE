package com.example.aboutme.Login.controller;

import com.example.aboutme.Login.dto.MsgDTO;
import com.example.aboutme.Login.dto.SocialInfoDTO;
import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.Login.service.GoogleService;
import com.example.aboutme.Login.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final TokenProvider tokenProvider;

//    @GetMapping("/kakaoLogin")
//    public String kakaoLogin(HttpServletResponse response) throws IOException {
//        String kakaoLoginUrl = kakaoService.getKakaoLogin();
//        response.sendRedirect(kakaoLoginUrl);
//        return "null";
//    }
//
//    @GetMapping("/googleLogin")
//    public String googleLogin(HttpServletResponse response) throws IOException {
//        String googleLoginUrl = googleService.getGoogleLogin();
//        response.sendRedirect(googleLoginUrl);
//        return "null";
//    }

    @GetMapping("members/{socialType}/login")
    public void socialLogin(HttpServletResponse response, @PathVariable String socialType) throws IOException {
        switch (socialType){
            case "kakao":
                String kakaoLoginUrl = kakaoService.getKakaoLogin();
                response.sendRedirect(kakaoLoginUrl);
                break;
            case "google":
                String googleLoginUrl = googleService.getGoogleLogin();
                response.sendRedirect(googleLoginUrl);
                break;
        }
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<MsgDTO> kakaoCallback(HttpServletRequest request) throws Exception {
        SocialInfoDTO.KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
        kakaoService.saveKakaoMember(kakaoInfo);
        return ResponseEntity.ok()
                .body(new MsgDTO("Success", kakaoInfo, tokenProvider.createToken(kakaoInfo.getEmail())));
    }

    @GetMapping("/google/callback")
    public ResponseEntity<MsgDTO> googleCallback(HttpServletRequest request) throws Exception {
        SocialInfoDTO.GoogleDTO googleInfo = googleService.getGoogleInfo(request.getParameter("code"));
        googleService.saveGoogleMember(googleInfo);
        return ResponseEntity.ok()
                .body(new MsgDTO("Success", googleInfo, tokenProvider.createToken(googleInfo.getEmail())));
    }


}
