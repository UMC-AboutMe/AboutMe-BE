package com.example.aboutme.converter;

import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.PlanResponse;
import com.example.aboutme.domain.Plan;
import com.example.aboutme.domain.Space;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PlanConverter {

    public static Plan toPlan(Space space, PlanRequest.CreatePlanDTO createPlanDTO) throws ParseException {
        return Plan.builder()
                .content(createPlanDTO.getContent())
                .date(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(DateTime.now())))
                .space(space)
                .build();
    }

    public static PlanResponse.planDTO toPlanDTO(Plan plan) {
        return PlanResponse.planDTO.builder()
                .content(plan.getContent())
                .date(plan.getDate().toString())
                .build();
    }

}
