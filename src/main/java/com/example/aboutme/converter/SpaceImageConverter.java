package com.example.aboutme.converter;

import com.example.aboutme.aws.s3.S3ResponseDto;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.SpaceImage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpaceImageConverter {
    public static SpaceImage toSpaceImage(Space space, S3ResponseDto imageDTO) {
        return SpaceImage.builder()
                .image(imageDTO.getImgUrl())
                .space(space)
                .build();
    }
}
