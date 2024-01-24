package com.example.aboutme.app.dto;

import lombok.Getter;

public class ProfileRequest {

    @Getter
    public static class CreateProfileDTO{
        private String name;
    }
}
