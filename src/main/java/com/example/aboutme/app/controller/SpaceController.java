package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.app.dto.SpaceResponse;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.SpaceService.SpaceCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myspaces")
@Slf4j
public class SpaceController {
    private final SpaceCommandService spaceCommandService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.JoinResultDTO> join (@RequestBody @Valid SpaceRequest.JoinDTO request) {
        Space newSpace = spaceCommandService.JoinSpace(request);
        return ApiResponse.onSuccess(SpaceConverter.toJoinResultDTO(newSpace));
    }
}