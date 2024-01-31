package com.example.aboutme.converter;

import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.app.dto.SpaceResponse;
import com.example.aboutme.domain.Space;

import java.util.ArrayList;

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
        return SpaceResponse.ReadResultDTO.builder()
                .nickname(space.getNickname())
                .characterType(space.getCharacterType())
                .roomType(space.getRoomType())
                .mood(space.getMood())
                .musicUrl(space.getMusicUrl())
                .statusMessage(space.getStatusMessage())
                .spaceImageList(space.getSpaceImageList())
                .planList(space.getPlanList())
                .build();
    }

    public static SpaceResponse.DeleteDTO toDeleteDTO(Space deleteSpace) {
        return SpaceResponse.DeleteDTO.builder()
                .nickname(deleteSpace.getNickname())
                .characterType(deleteSpace.getCharacterType())
                .roomType(deleteSpace.getRoomType())
                .build();
    }
}
