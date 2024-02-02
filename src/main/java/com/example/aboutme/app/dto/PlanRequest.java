package com.example.aboutme.app.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class PlanRequest {
    @Getter
    public static class CreatePlanDTO{
        @NotEmpty
        private String content;

        @NotEmpty
        private Date date;
    }
}
