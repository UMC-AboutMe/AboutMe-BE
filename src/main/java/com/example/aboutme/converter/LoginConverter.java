package com.example.aboutme.converter;

import com.example.aboutme.Login.dto.MsgResponse;

public class LoginConverter {
    public static MsgResponse.LoginMsgDTO toLoginDTO(String msg, String email, String token){
        return MsgResponse.LoginMsgDTO.builder()
                .msg(msg)
                .email(email)
                .token(token)
                .build();
    }

    public static MsgResponse.validMsgDTO toValidMsgDTO(String msg){
        return MsgResponse.validMsgDTO.builder().msg(msg).build();
    }
}
