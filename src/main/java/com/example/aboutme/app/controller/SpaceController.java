package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.apiPayload.code.BaseCode;
import com.example.aboutme.apiPayload.code.status.SuccessStatus;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.app.dto.SpaceResponse;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.SpaceService.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myspaces")
@Slf4j
public class SpaceController {
    private final SpaceService spaceService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.JoinResultDTO> join (@RequestBody @Valid SpaceRequest.JoinDTO request) {
        Space newSpace = spaceService.JoinSpace(request);
        return ApiResponse.onSuccess(SpaceConverter.toJoinResultDTO(newSpace));
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> read(@RequestHeader("member_id") Long memberId) {
        Space space = spaceService.readSpace(memberId);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(space));
    }

    @DeleteMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.DeleteResultDTO> delete(@RequestHeader("member_id") Long memberId) {
        spaceService.deleteSpace(memberId);
        return ApiResponse.of(SuccessStatus._MYSPACE_DELETE, null);
    }
}
