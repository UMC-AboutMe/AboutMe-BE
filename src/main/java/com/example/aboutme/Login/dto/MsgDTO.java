package com.example.aboutme.Login.dto;

import com.example.aboutme.Login.jwt.JwtDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgDTO {

    private String msg;
    private Object result;
    private JwtDTO jwtToken;

    public MsgDTO(String msg, Object result, JwtDTO token) {
        this.msg = msg;
        this.result  = result;
        this.jwtToken = token;
    }
}
