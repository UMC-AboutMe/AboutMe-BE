package com.example.aboutme.converter;

import com.example.aboutme.domain.SpaceImage;

public class SpaceImageConverter {
    public static SpaceImage toSpaceImage(String imgUrl) {
        return SpaceImage.builder()
                .image(imgUrl)
                .build();
    }
}
