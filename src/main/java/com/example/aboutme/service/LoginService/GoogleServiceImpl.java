package com.example.aboutme.service.LoginService;

import com.example.aboutme.converter.MemberConverter;
import com.example.aboutme.app.dto.SocialInfoRequest;
import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import com.example.aboutme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class GoogleServiceImpl implements GoogleService{
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;
    public String getGoogleLogin() {
        return "https://accounts.google.com/o/oauth2/v2/auth?client_id="
                + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_REDIRECT_URL
                + "&response_type=code" + "&scope=email";
    }
    public SocialInfoRequest.GoogleDTO getGoogleInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , GOOGLE_CLIENT_ID);
            params.add("client_secret", GOOGLE_CLIENT_SECRET);
            params.add("code"         , code);
            params.add("redirect_uri" , GOOGLE_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://oauth2.googleapis.com/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");

            System.out.println("acct:"+accessToken);
        } catch (Exception e) {
//            throw new Exception("API call failed");
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }
        System.out.println(accessToken);
        return getUserInfoWithToken(accessToken);
    }

    public SocialInfoRequest.GoogleDTO getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        RequestEntity<Void> request = RequestEntity.get(URI.create(userInfoEndpointUri)).headers(headers).build();
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(request, String.class);
        System.out.println(responseEntity);

        // 응답에서 이메일 주소 가져오기
        JsonParser jsonParser = JsonParserFactory.getJsonParser();

        // JSON 문자열을 Map으로 파싱
        Map<String, Object> jsonMap = jsonParser.parseMap(responseEntity.getBody());

        // "sub"와 "email" 키를 사용하여 값을 추출
        String email = (String) jsonMap.get("email");
        System.out.println(email);


        return SocialInfoRequest.GoogleDTO.builder()
                .email(email)
                .build();
    }


    public Member saveGoogleMember(SocialInfoRequest.GoogleDTO googleDTO){
        String newToken = tokenProvider.createToken(googleDTO.getEmail(), "GOOGLE");
        Member newMember = MemberConverter.toMember(googleDTO, Social.GOOGLE,newToken);
        Boolean principal = memberRepository.existsByEmailAndSocial(newMember.getEmail(),Social.GOOGLE);
        if (principal == false){
            memberRepository.save(newMember);
        }
        return newMember;
    }
}
