package com.example.aboutme.Login.controller;

import com.example.aboutme.Login.dto.MsgDTO;
import com.example.aboutme.Login.dto.SocialInfoDTO;
//import com.example.aboutme.Login.jwt.JwtAuthenticationFilter;
import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.Login.service.GoogleService;
import com.example.aboutme.Login.service.KakaoService;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @GetMapping("members/{socialType}/login")
    public void socialLogin(HttpServletResponse response, @PathVariable String socialType) throws IOException {
        switch (socialType) {
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

//    @GetMapping("/{socialType}}/callback")
//    public ResponseEntity<MsgDTO> socialCallback(HttpServletRequest request, @PathVariable String socialType) throws Exception {
//        switch (socialType) {
//            case "kakao":
//                SocialInfoDTO.KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
//                kakaoService.saveKakaoMember(kakaoInfo);
//                return ResponseEntity.ok()
//                        .body(new MsgDTO("Success", kakaoInfo, tokenProvider.createToken(kakaoInfo.getEmail())));
//            case "google":
//                SocialInfoDTO.GoogleDTO googleInfo = googleService.getGoogleInfo(request.getParameter("code"));
//                googleService.saveGoogleMember(googleInfo);
//                return ResponseEntity.ok()
//                        .body(new MsgDTO("Success", googleInfo, tokenProvider.createToken(googleInfo.getEmail())));
//            default:
//                return ResponseEntity.ok()
//                        .body(new MsgDTO("fail", null, null));
//
//        }
//    }

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

    @GetMapping("/members/valid")
    public ResponseEntity<MsgDTO.validMsg> isValidToken(@RequestBody String token) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(token);
        boolean res = tokenProvider.validateToken((String) jsonObj.get("token"));
        return ResponseEntity.ok().body(new MsgDTO.validMsg("Success"));
    }



//--------------------------------------
//    @PostMapping("/api/auth/login")
//    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody String email, @RequestBody String social) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        email,
//                        social
//                );
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Long userId = memberRepository.findByEmailAndSocial(email, Social.valueOf(social.toUpperCase())).getId();
//        String jwt = tokenProvider.createToken(email);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        String userEmail = tokenProvider.getEmailFromToken(jwt);
//        TokenDTO tokenDto = TokenDTO.builder().token(jwt).email(userEmail).build();
//
//        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
//    }

}
