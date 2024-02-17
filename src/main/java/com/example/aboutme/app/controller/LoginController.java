package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.MsgResponse;
import com.example.aboutme.app.dto.SocialInfoRequest;
//import com.example.aboutme.Login.jwt.JwtAuthenticationFilter;
import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.service.LoginService.GoogleService;
import com.example.aboutme.service.LoginService.KakaoService;
import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.converter.LoginConverter;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @GetMapping("tokenTest")
    public void tokenTest(@RequestHeader("token") String token){
        System.out.println(tokenProvider.getTokenInfoFromToken(token).getEmail());
        System.out.println(tokenProvider.getTokenInfoFromToken(token).getSocial());
    }

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
    public ApiResponse<MsgResponse.LoginMsgDTO> kakaoCallback(HttpServletRequest request) throws Exception {
        SocialInfoRequest.KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
        String newToken = tokenProvider.createToken(kakaoInfo.getEmail(),"KAKAO");
        kakaoService.saveKakaoMember(kakaoInfo);
        return ApiResponse.onSuccess(LoginConverter.toLoginDTO(kakaoInfo.getEmail(),newToken,Social.KAKAO));
    }

    @GetMapping("/google/callback")
    public ApiResponse<MsgResponse.LoginMsgDTO> googleCallback(HttpServletRequest request) throws Exception {
        SocialInfoRequest.GoogleDTO googleInfo = googleService.getGoogleInfo(request.getParameter("code"));
        String newToken = tokenProvider.createToken(googleInfo.getEmail(),"GOOGLE");
        googleService.saveGoogleMember(googleInfo);
        return ApiResponse.onSuccess(LoginConverter.toLoginDTO(googleInfo.getEmail(),newToken,Social.GOOGLE));
    }

    @GetMapping("/members/valid")
    public ApiResponse<MsgResponse.validMsgDTO> isValidToken(@RequestBody String token) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(token);
        boolean res = tokenProvider.validateToken((String) jsonObj.get("token"));
        if (res == true){
            return ApiResponse.onSuccess(LoginConverter.toValidMsgDTO("success"));
        }else {
            return ApiResponse.onSuccess(LoginConverter.toValidMsgDTO("fail"));
        }
    }

    @DeleteMapping("/members/unregister")
    private ApiResponse<MsgResponse.unregisterMsgDTO> unregisterMember(@RequestHeader("token") String token){
        String email = tokenProvider.getEmailFromToken(token);
        memberService.deleteMember(email);
        return ApiResponse.onSuccess(LoginConverter.toUnregisterMsgDTO(email,"unregister Member"));
    }

    @PostMapping("members/{socialType}/login")
    public ApiResponse<MsgResponse.LoginMsgDTO> androidFrontLogin(@PathVariable String socialType, @RequestBody String token) throws Exception {
        String newJwtToken;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(token);
        String frontAccessToken = (String) jsonObj.get("token");
        System.out.println(frontAccessToken);

        switch (socialType) {
            case "kakao":
                SocialInfoRequest.KakaoDTO kakaoInfo = kakaoService.getUserInfoWithToken(frontAccessToken);
                newJwtToken = tokenProvider.createToken(kakaoInfo.getEmail(), "KAKAO");
                Member newMemberKakao = kakaoService.saveKakaoMember(kakaoInfo);
                return ApiResponse.onSuccess(LoginConverter.toLoginDTO(kakaoInfo.getEmail(),newJwtToken, Social.KAKAO));
            case "google":
                SocialInfoRequest.GoogleDTO googleInfo = googleService.getUserInfoWithToken(frontAccessToken);
                newJwtToken = tokenProvider.createToken(googleInfo.getEmail(), "GOOGLE");
                Member newMemberGoogle = googleService.saveGoogleMember(googleInfo);
                return ApiResponse.onSuccess(LoginConverter.toLoginDTO(googleInfo.getEmail(),newJwtToken,Social.GOOGLE));
            default:
                throw new GeneralException(ErrorStatus.UNKNOWN_SOCIALTYPE);
        }
    }

    @PostMapping("members/{socialType}/loginEmail")
    public ApiResponse<MsgResponse.LoginMsgDTO> loginWithEmail(@PathVariable String socialType, @RequestBody String email) throws Exception{
        String newJwtToken;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(email);
        String parsedEmail = (String) jsonObj.get("email");

        switch (socialType) {
            case "kakao":
                newJwtToken = tokenProvider.createToken(parsedEmail, "KAKAO");
                SocialInfoRequest.KakaoDTO  kakaoInfo = SocialInfoRequest.KakaoDTO.builder().email(parsedEmail).nickname("").build();
                Member newMemberKakao = kakaoService.saveKakaoMember(kakaoInfo);
                return ApiResponse.onSuccess(LoginConverter.toLoginDTO(kakaoInfo.getEmail(),newJwtToken, Social.KAKAO));
            case "google":
                newJwtToken = tokenProvider.createToken(parsedEmail, "GOOGLE");
                SocialInfoRequest.GoogleDTO googleInfo = SocialInfoRequest.GoogleDTO.builder().email(parsedEmail).build();
                Member newMemberGoogle = googleService.saveGoogleMember(googleInfo);
                return ApiResponse.onSuccess(LoginConverter.toLoginDTO(googleInfo.getEmail(),newJwtToken,Social.GOOGLE));
            default:
                throw new GeneralException(ErrorStatus.UNKNOWN_SOCIALTYPE);
        }
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
