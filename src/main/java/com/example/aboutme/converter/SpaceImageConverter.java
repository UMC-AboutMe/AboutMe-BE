package com.example.aboutme.converter;

import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.SpaceImage;

public class SpaceImageConverter {
    public static SpaceImage toSpaceImage(Space space, String imgUrl) {
        return SpaceImage.builder()
                .image(imgUrl)
                .space(space)
                .build();
    }
}
