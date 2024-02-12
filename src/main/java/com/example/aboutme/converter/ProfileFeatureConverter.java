package com.example.aboutme.converter;

import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.constant.Side;

import java.util.ArrayList;
import java.util.List;

public class ProfileFeatureConverter {

    public static List<ProfileFeature> toDefaultProfileFeatureList(int frontNum, int backNum, String name){

        List<ProfileFeature> profileFeatureList = new ArrayList<>();

        profileFeatureList.add(
                ProfileFeature.builder()
                        .profileKey("name")
                        .profileValue(name)
                        .side(Side.FRONT)
                        .build()
        );

        for(int i=0; i<frontNum-1; i++){
            profileFeatureList.add(
                ProfileFeature.builder()
                        .side(Side.FRONT)
                        .build()
            );
        }

        for(int i=0; i<backNum; i++){
            profileFeatureList.add(
                    ProfileFeature.builder()
                            .side(Side.BACK)
                            .build()
            );
        }

        return profileFeatureList;
    }
}
