package com.example.aboutme.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class ResponseDto<T> {

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseDto<T> onSuccess(Code code, T data){
        return new ResponseDto<>(code.getCode(), code.getMessage(), data);
    }

    public static <T> ResponseDto<T> onFailure(Code code, T data){
        return new ResponseDto<>(code.getCode(), code.getMessage(), data);
    }
}
