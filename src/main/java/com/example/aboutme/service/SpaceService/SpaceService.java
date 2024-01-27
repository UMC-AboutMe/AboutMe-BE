package com.example.aboutme.service.SpaceService;

import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.domain.Space;

public interface SpaceService {
    Space JoinSpace(SpaceRequest.JoinDTO request);

    Space readSpace(Long memberId);
}
