package com.example.aboutme.converter;

import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.PlanResponse;
import com.example.aboutme.domain.Plan;

public class PlanConverter {

    public static Plan toPlan(PlanRequest.CreatePlanDTO createPlanDTO) {
        return Plan.builder()
                .content(createPlanDTO.getContent())
                .date(createPlanDTO.getDate())
                .build();
    }

    public static PlanResponse.CreatePlanDTO toCreatePlanDTO(Plan plan) {
        return PlanResponse.CreatePlanDTO.builder()
                .content(plan.getContent())
                .date(plan.getDate())
                .build();
    }
}
