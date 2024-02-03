package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MsgResponse;

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

    public static MsgResponse.unregisterMsgDTO toUnregisterMsgDTO(Long memberId, String msg){
        return MsgResponse.unregisterMsgDTO.builder()
                .memberId(memberId)
                .msg(msg).build();
    }
}
