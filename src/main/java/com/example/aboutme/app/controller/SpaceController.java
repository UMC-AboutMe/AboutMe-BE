package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.PlanRequest;
import com.example.aboutme.app.dto.PlanResponse;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.app.dto.SpaceResponse;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.SpaceService.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myspaces")
@Slf4j
public class SpaceController {
    private final SpaceService spaceCommandService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.JoinResultDTO> join (@RequestBody @Valid SpaceRequest.JoinDTO request) {
        Space newSpace = spaceCommandService.JoinSpace(request);
        return ApiResponse.onSuccess(SpaceConverter.toJoinResultDTO(newSpace));
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> read(@RequestHeader("member-id") Long memberId) {
        Space newSpace = spaceCommandService.readSpace(memberId);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @DeleteMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse delete(@RequestHeader("member-id") Long memberId) {
        spaceCommandService.deleteSpace(memberId);
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.UpdateResultDTO> update(@RequestHeader("member-id") Long memberId, @RequestBody @Valid SpaceRequest.UpdateDTO request) {
        Space updateSpace = spaceCommandService.updateResult(memberId, request);
        return ApiResponse.onSuccess(SpaceConverter.toUpdateResultDTO(updateSpace));
    }
  
    @PostMapping(value = "/plans/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> join (@RequestHeader("member-id") Long memberId, @RequestBody @Valid PlanRequest.CreatePlanDTO request) throws ParseException {
        Space newSpace = spaceCommandService.createPlan(memberId, request);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @PostMapping(value = "/images/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> uploadImage (@RequestHeader("member-id") Long memberId,
                                                                 @RequestPart(value = "file", required = false) @NotEmpty MultipartFile multipartFile) {
        Space newSpace = spaceCommandService.uploadImage(memberId, multipartFile);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }
}
