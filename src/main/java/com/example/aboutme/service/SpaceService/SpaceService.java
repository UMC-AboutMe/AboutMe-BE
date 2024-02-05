package com.example.aboutme.service.SpaceService;

import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.domain.Space;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Optional;

public interface SpaceService {
    Space JoinSpace(Long memberId, SpaceRequest.JoinDTO request);

    Space readSpace(Long memberId);

    void deleteSpace(Long memberId);

    Space updateResult(Long memberId, SpaceRequest.UpdateDTO request);
    
    Space createPlan(Long memberId, PlanRequest.CreatePlanDTO request) throws ParseException;

    Space uploadImage(Long memberId, MultipartFile multipartFile);

    Space searchSpace(String keyword);
}
