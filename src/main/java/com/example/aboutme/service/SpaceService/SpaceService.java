package com.example.aboutme.service.SpaceService;

import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.domain.Space;

import java.text.ParseException;

public interface SpaceService {
    Space JoinSpace(SpaceRequest.JoinDTO request);

    Space readSpace(Long memberId);

    void deleteSpace(Long memberId);

    Space updateResult(Long memberId, SpaceRequest.UpdateDTO request);
    
    Space createPlan(Long memberId, PlanRequest.CreatePlanDTO request) throws ParseException;

}
