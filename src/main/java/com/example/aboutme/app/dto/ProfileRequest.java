package com.example.aboutme.app.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class ProfileRequest {

    @Getter
    public static class CreateProfileDTO{
        @NotEmpty
        private String name;
    }
}
