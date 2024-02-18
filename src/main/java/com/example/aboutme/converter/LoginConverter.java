package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MsgResponse;
import com.example.aboutme.domain.constant.Social;

public class LoginConverter {
    public static MsgResponse.LoginMsgDTO toLoginDTO(String email, String token, Social social){
        return MsgResponse.LoginMsgDTO.builder()
                .email(email)
                .jwtToken(token)
                .social(social)
                .build();
    }

    public static MsgResponse.validMsgDTO toValidMsgDTO(String msg){
        return MsgResponse.validMsgDTO.builder().msg(msg).build();
    }

    public static MsgResponse.unregisterMsgDTO toUnregisterMsgDTO(String email, String msg){
        return MsgResponse.unregisterMsgDTO.builder()
                .email(email)
                .msg(msg).build();
    }
}
