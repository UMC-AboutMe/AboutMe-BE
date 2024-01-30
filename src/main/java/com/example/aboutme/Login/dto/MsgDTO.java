package com.example.aboutme.Login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgDTO {

    private String msg;
    private Object result;
    private String jwtToken;

    public MsgDTO(String msg, Object result, String token) {
        this.msg = msg;
        this.result  = result;
        this.jwtToken = token;
    }
}
