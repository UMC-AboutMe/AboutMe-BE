package com.example.aboutme.converter;

import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.domain.mapping.MemberSpace;

import java.util.List;

public class MemberSpaceConverter {

    public static MemberSpaceResponse.GetListDto toGetMemberSpaceListDTO(List<MemberSpace> memberSpaceList) {
        List<MemberSpaceResponse.getDto> memberSpaceListDto = memberSpaceList.stream()
                .map(memberSpace -> MemberSpaceResponse.getDto.builder()
                        .spaceId(memberSpace.getSpace().getId())
                        .nickname(memberSpace.getSpace().getNickname())
                        .characterType(memberSpace.getSpace().getCharacterType())
                        .roomType(memberSpace.getSpace().getRoomType())
                        .favorite(memberSpace.isFavorite())
                        .build())
                .toList();

        return new MemberSpaceResponse.GetListDto(memberSpaceListDto);
    }

    public static MemberSpaceResponse.favoriteDto toToggleFavorite(Boolean favorite) {
        return MemberSpaceResponse.favoriteDto.builder()
                .favorite(favorite)
                .build();
    }

    public static MemberSpaceResponse.addDto toAddMemberSpaceDTO(MemberSpace memberSpace) {
        return MemberSpaceResponse.addDto.builder()
                .spaceId(memberSpace.getSpace().getId())
                .build();
    }
}
