package com.example.aboutme.app.controller;

import com.example.aboutme.Login.jwt.TokenProvider;
import com.example.aboutme.apiPayload.ApiResponse;
import com.example.aboutme.app.dto.*;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.AlarmService.AlarmService;
import com.example.aboutme.service.MemberService.MemberService;
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
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.JoinResultDTO> join (@RequestHeader("token") String token, @RequestBody @Valid SpaceRequest.JoinDTO request) {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Space newSpace = spaceService.JoinSpace(member.getId(), request);
        return ApiResponse.onSuccess(SpaceConverter.toJoinResultDTO(newSpace));
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> read(@RequestHeader("token") String token) {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Space newSpace = spaceService.readSpace(member.getId());
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @DeleteMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse delete(@RequestHeader("token") String token) {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        spaceService.deleteSpace(member.getId());
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.UpdateResultDTO> update(@RequestHeader("token") String token, @RequestBody @Valid SpaceRequest.UpdateDTO request) {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Space updateSpace = spaceService.updateResult(member.getId(), request);
        return ApiResponse.onSuccess(SpaceConverter.toUpdateResultDTO(updateSpace));
    }

    @PostMapping(value = "/plans", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> join (@RequestHeader("token") String token, @RequestBody @Valid PlanRequest.CreatePlanDTO request) throws ParseException {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Space newSpace = spaceService.createPlan(member.getId(), request);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @PostMapping(value = "/images", produces = "application/json;charset=UTF-8")
    public ApiResponse<SpaceResponse.ReadResultDTO> uploadImage (@RequestHeader("token") String token,
                                                                 @RequestPart(value = "file", required = false) @NotEmpty MultipartFile multipartFile) {

        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Space newSpace = spaceService.uploadImage(member.getId(), multipartFile);
        return ApiResponse.onSuccess(SpaceConverter.toReadResultDTO(newSpace));
    }

    @GetMapping("/search")
    public ApiResponse<SpaceResponse.SearchResultDto> search(@RequestParam(defaultValue = "") String keyword) {
        Space space = spaceService.searchSpace(keyword);
        return ApiResponse.onSuccess(SpaceConverter.toSearchResultDTO(space));
    }

    @PostMapping(value = "/shares", produces = "application/json;charset=UTF-8")
    public ApiResponse<AlarmResponse.JoinResultDTO> share (@RequestHeader("token") String token, @RequestBody @Valid AlarmRequest.CreateDTO request) {
        String email = tokenProvider.getEmailFromToken(token);
        Member member = memberService.findMember(email);
        Alarm newAlarm = alarmService.shareSpace(member.getId(), request);
        return ApiResponse.onSuccess(AlarmConverter.toJoinResultDTO(newAlarm));
    }
}
