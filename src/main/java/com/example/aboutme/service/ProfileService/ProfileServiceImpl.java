package com.example.aboutme.service.ProfileService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.converter.ProfileFeatureConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final MemberService memberService;
    private final ProfileRepository profileRepository;

    /**
     * 내 마이프로필 조회
     * @param memberId 멤버 식별자
     * @return
     */
    public List<Profile> getMyProfiles(Long memberId){
        Member member = memberService.findMember(memberId);

        return profileRepository.findAllByMemberOrderByIsDefaultDesc(member);
    }

    /**
     * 마이프로필 생성
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    @Transactional
    public Profile createMyProfile(Long memberId, ProfileRequest.CreateProfileDTO request){
        Member member = memberService.findMember(memberId);

        // 최대 생성 개수 초과하는지 확인
        int MAX_PROFILE_SIZE = 3;
        if(profileRepository.countByMember(member) >= MAX_PROFILE_SIZE){
            throw new GeneralException(ErrorStatus.PROFILE_SIZE_OVERFLOW);
        }

        Profile newProfile = ProfileConverter.toProfile(generateSerialNumber());

        newProfile.setMember(member);

        int DEFAULT_FRONT_FEATURE_SIZE = 2;
        int DEFAULT_BACK_FEATURE_SIZE = 5;
        List<ProfileFeature> defaultProfileFeatureList = ProfileFeatureConverter.toDefaultProfileFeatureList(DEFAULT_FRONT_FEATURE_SIZE, DEFAULT_BACK_FEATURE_SIZE, request.getName());
        for(ProfileFeature profileFeature : defaultProfileFeatureList){
            profileFeature.setProfile(newProfile);
        }

        profileRepository.save(newProfile);

        return newProfile;
    }

    /**
     * 시리얼 넘버 생성
     * @return 중복되지 않는 시리얼 넘버
     */
    private int generateSerialNumber(){
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());

        int serialNumber = generator.nextInt(1000000) % 1000000;

        // 중복 확인
        while(profileRepository.findBySerialNumber(serialNumber).isPresent()){
            serialNumber = generator.nextInt(1000000) % 1000000;
        }

        return serialNumber;
    }
}
