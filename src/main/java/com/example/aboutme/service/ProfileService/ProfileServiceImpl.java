package com.example.aboutme.service.ProfileService;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.aws.s3.S3ResponseDto;
import com.example.aboutme.aws.s3.S3Service;
import com.example.aboutme.converter.ProfileConverter;
import com.example.aboutme.converter.ProfileFeatureConverter;
import com.example.aboutme.converter.ProfileImageConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.ProfileImage;
import com.example.aboutme.domain.constant.ProfileImageType;
import com.example.aboutme.repository.ProfileFeatureRepository;
import com.example.aboutme.repository.ProfileImageRepository;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService{

    private final MemberService memberService;
    private final ProfileRepository profileRepository;
    private final ProfileFeatureRepository profileFeatureRepository;
    private final ProfileImageRepository profileImageRepository;
    private final S3Service s3Service;

    /**
     * 내 마이프로필 목록 조회
     * @param tokenClaimsDTO 멤버 식별자
     * @return 마이프로필 목록
     */
    public List<Profile> getMyProfiles(TokenDTO.tokenClaimsDTO tokenClaimsDTO){
        Member member = memberService.findMember(tokenClaimsDTO);

        return profileRepository.findAllByMemberOrderByIsDefaultDesc(member);
    }

    public List<Profile> getMyProfiles(String email){
        Member member = memberService.findMember(email);

        return profileRepository.findAllByMemberOrderByIsDefaultDesc(member);
    }

    /**
     * 마이프로필 단건 조회
     * @param profileId 프로필 식별자
     * @return 마이프로필
     */
    public Profile getMyProfile(Long profileId){

        return profileRepository.findById(profileId).get();
    }

    /**
     * 마이프로필 생성
     * @param tokenClaimsDTO
     * @param request
     * @return
     */
    @Transactional
    public Profile createMyProfile(TokenDTO.tokenClaimsDTO tokenClaimsDTO, ProfileRequest.CreateProfileDTO request){
        Member member = memberService.findMember(tokenClaimsDTO);

        // 최대 생성 개수 초과하는지 확인
        int MAX_PROFILE_SIZE = 3;
        if(profileRepository.countByMember(member) >= MAX_PROFILE_SIZE){
            throw new GeneralException(ErrorStatus.PROFILE_SIZE_OVERFLOW);
        }

        Profile newProfile = ProfileConverter.toProfile(generateSerialNumber());
        ProfileImage profileImage = ProfileImageConverter.toProfileImage(newProfile, member.getSpace());

        newProfile.setMember(member);

        int DEFAULT_FRONT_FEATURE_SIZE = 2;
        int DEFAULT_BACK_FEATURE_SIZE = 5;
        List<ProfileFeature> defaultProfileFeatureList = ProfileFeatureConverter.toDefaultProfileFeatureList(DEFAULT_FRONT_FEATURE_SIZE, DEFAULT_BACK_FEATURE_SIZE, request.getName());
        for(ProfileFeature profileFeature : defaultProfileFeatureList){
            profileFeature.setProfile(newProfile);
        }

        profileRepository.save(newProfile);
        profileImageRepository.save(profileImage);

        return newProfile;
    }

    /**
     * 내 마이프로필 수정
     * @param tokenClaimsDTO 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return 수정된 마이프로필의 특징
     */
    @Transactional
    public ProfileFeature updateMyProfile(TokenDTO.tokenClaimsDTO tokenClaimsDTO, Long profileId, ProfileRequest.UpdateProfileDTO request){
        Member member = memberService.findMember(tokenClaimsDTO);
        Profile profile = profileRepository.findById(profileId).get();
        ProfileFeature profileFeature = profileFeatureRepository.findById(request.getFeatureId()).get();

        if(profile.getMember() != member){
            throw new GeneralException(ErrorStatus.PROFILE_NOT_MATCH_MEMBER_AT_UPDATE);
        }

        if(profileFeature.getProfile() != profile){
            throw new GeneralException(ErrorStatus.PROFILE_FEATURE_NOT_MATCH_PROFILE);
        }

        // 이름은 필수
        boolean isName = profileFeature.getProfileKey() != null && profileFeature.getProfileKey().equals("name");
        if(isName){
            boolean isNameEmpty = request.getFeatureKey() == null || !request.getFeatureKey().equals("name") || request.getFeatureValue() == null || request.getFeatureValue().isEmpty();
            if(isNameEmpty){
                throw new GeneralException(ErrorStatus.PROFILE_FEATURE_NAME_CANNOT_EMPTY);
            }
        }

        profileFeature.update(request.getFeatureKey(), request.getFeatureValue());

        return profileFeature;
    }

    /**
     * 내 마이프로필 이미지 수정
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param image 이미지
     * @param request
     * @return 수정된 마이프로필 이미지
     */
    @Transactional
    public ProfileImage updateMyProfileImage(Long memberId, Long profileId, MultipartFile image, ProfileRequest.UpdateProfileImageDTO request){

        Member member = memberService.findMember(memberId);
        Profile profile = profileRepository.findById(profileId).get();

        if(!profile.getMember().getId().equals(member.getId())){
            throw new GeneralException(ErrorStatus.PROFILE_NOT_MATCH_MEMBER_AT_UPDATE);
        }

        ProfileImage profileImage = profile.getProfileImage();

        ProfileImageType profileImageType = ProfileImageType.valueOf(request.getProfileImageType());
        switch (profileImageType){
            case USER_IMAGE -> {
                S3ResponseDto s3ResponseDto = s3Service.uploadFile(image);
//                log.info("이미지 url: {}", s3ResponseDto.getImgUrl());
                profileImage.update(profileImageType, s3ResponseDto.getImgUrl());
            }
            case CHARACTER -> {
                boolean illegalChangeToCharacter = (member.getSpace() == null);
                if(illegalChangeToCharacter){
                    throw new GeneralException(ErrorStatus.PROFILE_IMAGE_CANNOT_CHANGE_TO_CHARACTER);
                }

                profileImage.update(profileImageType, member.getSpace());
            }
            default -> {
                profileImage.update(profileImageType);
            }
        }

        return profileImage;
    }

    /**
     * 내 마이프로필 삭제
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     */
    @Transactional
    public void deleteMyProfile(Long memberId, Long profileId){
        Member member = memberService.findMember(memberId);
        Profile profile = profileRepository.findById(profileId).get();

        if(profile.getMember() != member){
            throw new GeneralException(ErrorStatus.PROFILE_NOT_MATCH_MEMBER_AT_DELETE);
        }

        profileRepository.delete(profile);
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
        boolean isDuplicated = profileRepository.findBySerialNumber(serialNumber).isPresent();
        while(isDuplicated){
            serialNumber = generator.nextInt(1000000) % 1000000;

            isDuplicated = profileRepository.findBySerialNumber(serialNumber).isPresent();
        }

        return serialNumber;
    }

    @Transactional
    public Profile updateIsDefault(String email, Long profileId) {
        List<Profile> profileList = getMyProfiles(email);
        profileList.forEach(
                profile -> {
                    if (profile.getIsDefault()) {
                        profile.setIsDefault(false);
                    }
                }
        );
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                ()-> new GeneralException(ErrorStatus.PROFILE_NOT_FOUND)
        );

        profile.setIsDefault(true);
        return profile;
    }

    @Transactional
    public Profile updateIsDefaultToFalse(String email, Long profileId) {
        Member member = memberService.findMember(email);
        Profile myProfile = profileRepository.findByMemberAndId(member,profileId).orElseThrow(
                () -> new GeneralException(ErrorStatus.PROFILE_NOT_FOUND)
        );
        if (myProfile.getIsDefault()){
            myProfile.setIsDefault(false);
        }
        return myProfile;
    }

    /**
     * 프로필 검색
     * @param serialNumber 시리얼 넘버
     * @return 검색된 프로필
     */
    public Profile searchProfile(int serialNumber){

        Profile profile = profileRepository.findBySerialNumber(serialNumber).get();

        return profile;
    }
}
