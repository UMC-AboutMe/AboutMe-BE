package com.example.aboutme.Login.controller;

import com.example.aboutme.Login.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final KakaoService kakaoService;

    @GetMapping("/login")
    public String login(HttpServletResponse response) throws IOException {
        String kakaoLoginUrl = kakaoService.getKakaoLogin();
        response.sendRedirect(kakaoLoginUrl);
        return "null";
    }
}
