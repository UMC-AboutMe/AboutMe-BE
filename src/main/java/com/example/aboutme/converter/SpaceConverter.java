package com.example.aboutme.converter;

import com.example.aboutme.app.dto.PlanResponse;
import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.app.dto.SpaceResponse;
import com.example.aboutme.aws.s3.S3ResponseDto;
import com.example.aboutme.aws.s3.S3Service;
import com.example.aboutme.domain.Plan;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.SpaceImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceConverter {

    public static SpaceResponse.JoinResultDTO toJoinResultDTO(Space space) {
        return SpaceResponse.JoinResultDTO.builder()
                .nickname(space.getNickname())
                .characterType(space.getCharacterType())
                .roomType(space.getRoomType())
                .build();
    }

    public static Space toSpace(SpaceRequest.JoinDTO request) {
        return Space.builder()
                .nickname(request.getNickname())
                .characterType(request.getCharacterType())
                .roomType(request.getRoomType())
                .spaceImageList(new ArrayList<>())
                .planList(new ArrayList<>())
                .build();
    }

    public static SpaceResponse.ReadResultDTO toReadResultDTO(Space space) {
        List<PlanResponse.planDTO> readPlanDTOList = new ArrayList<>();
        List<String> readImageDTOList = new ArrayList<>();

        space.getPlanList().stream()
                .map(PlanConverter::toPlanDTO)
                .forEach(readPlanDTOList::add);

        space.getSpaceImageList().stream()
                .map(SpaceImage::getImage)
                .forEach(readImageDTOList::add);

        return SpaceResponse.ReadResultDTO.builder()
                .nickname(space.getNickname())
                .characterType(space.getCharacterType())
                .roomType(space.getRoomType())
                .mood(space.getMood())
                .musicUrl(space.getMusicUrl())
                .statusMessage(space.getStatusMessage())
                .spaceImageList(readImageDTOList)
                .planList(readPlanDTOList)
                .build();
    }

    public static SpaceResponse.UpdateResultDTO toUpdateResultDTO(Space space) {
        List<String> readImageDTOList = new ArrayList<>();

        space.getSpaceImageList().stream()
                .map(SpaceImage::getImage)
                .forEach(readImageDTOList::add);

        return SpaceResponse.UpdateResultDTO.builder()
                .nickname(space.getNickname())
                .characterType(space.getCharacterType())
                .roomType(space.getRoomType())
                .mood(space.getMood())
                .musicUrl(space.getMusicUrl())
                .statusMessage(space.getStatusMessage())
                .spaceImageList(readImageDTOList)
                .planList(space.getPlanList())
                .build();
    }
}
