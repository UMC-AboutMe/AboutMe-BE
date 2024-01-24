package com.example.aboutme.apiPayload.exception.handler;

import com.example.aboutme.apiPayload.code.BaseErrorCode;
import com.example.aboutme.apiPayload.exception.GeneralException;

public class SpaceHandler extends GeneralException {
    public SpaceHandler(BaseErrorCode code) {
        super(code);
    }
}
