package com.example.aboutme.app.controller;

import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.*;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.AlarmService.AlarmService;
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
    private final SpaceService spaceService;
    private final AlarmService alarmService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.JoinResultDTO> join (@RequestHeader("member-id") Long memberId, @RequestBody @Valid SpaceRequest.JoinDTO request) {
        Space newSpace = spaceService.JoinSpace(memberId, request);
        return ApiResponse.onSuccess(SpaceConverter.toJoinResultDTO(newSpace));
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> read(@RequestHeader("member-id") Long memberId) {
        Space newSpace = spaceService.readSpace(memberId);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @DeleteMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse delete(@RequestHeader("member-id") Long memberId) {
        spaceService.deleteSpace(memberId);
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.UpdateResultDTO> update(@RequestHeader("member-id") Long memberId, @RequestBody @Valid SpaceRequest.UpdateDTO request) {
        Space updateSpace = spaceService.updateResult(memberId, request);
        return ApiResponse.onSuccess(SpaceConverter.toUpdateResultDTO(updateSpace));
    }

    @PostMapping(value = "/plans", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> join (@RequestHeader("member-id") Long memberId, @RequestBody @Valid PlanRequest.CreatePlanDTO request) throws ParseException {
        Space newSpace = spaceService.createPlan(memberId, request);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @PostMapping(value = "/images", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> uploadImage (@RequestHeader("member-id") Long memberId,
                                                                 @RequestPart(value = "file", required = false) @NotEmpty MultipartFile multipartFile) {
        Space newSpace = spaceService.uploadImage(memberId, multipartFile);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @GetMapping("/search")
    public ApiResponse<SpaceResponse.SearchResultDto> search(@RequestParam(defaultValue = "") String keyword) {
        Space space = spaceService.searchSpace(keyword);
        return ApiResponse.onSuccess(SpaceConverter.toSearchResultDTO(space));
    }

    @PostMapping(value = "/shares", produces = "application/json;charset=UTF-8")
    public ApiResponse<AlarmResponse.JoinResultDTO> share (@RequestHeader("member-id") Long memberId, @RequestBody @Valid AlarmRequest.CreateDTO request) {
        Alarm newAlarm = alarmService.shareSpace(memberId, request);
        return ApiResponse.onSuccess(AlarmConverter.toJoinResultDTO(newAlarm));
    }
}
