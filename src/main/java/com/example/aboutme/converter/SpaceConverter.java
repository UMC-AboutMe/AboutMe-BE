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
                .build();
    }

    public static Space toSpace(SpaceRequest.JoinDTO request) {
        return Space.builder()
                .nickname(request.getNickname())
                .characterType(request.getCharacterType())
                .spaceImageList(new ArrayList<>())
                .planList(new ArrayList<>())
                .build();
    }
}
