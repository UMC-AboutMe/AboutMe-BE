package com.example.aboutme.app.controller;

import com.example.aboutme.base.Code;
import com.example.aboutme.base.ResponseDto;
import com.example.aboutme.base.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TempController {

    @GetMapping("/test/success")
    public ResponseDto<String> testSuccess(){
        log.info("테스트 요청 (성공)");
        return ResponseDto.onSuccess(Code.OK, "성공 테스트 요청");
    }

    @GetMapping("/test/exception")
    public ResponseDto<String> testException(@RequestParam("on") Boolean on){

        if(on){
            log.info("테스트 요청 (예외처리)");
            throw new GeneralException(Code._INTERNAL_SERVER_ERROR);
        }

        log.info("테스트 요청 (성공)");
        return ResponseDto.onSuccess(Code.OK, "성공 테스트 요청");
    }
}
