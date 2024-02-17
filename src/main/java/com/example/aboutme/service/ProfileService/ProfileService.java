package com.example.aboutme.service.ProfileService;

import com.example.aboutme.Login.jwt.TokenDTO;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    /**
     * 내 마이프로필 목록 조회
     * @param tokenClaimsDTO 멤버 식별자
     * @return 마이프로필 목록
     */
    List<Profile> getMyProfiles(TokenDTO.tokenClaimsDTO tokenClaimsDTO);

    /**
     * 마이프로필 단건 조회
     * @param profileId 프로필 식별자
     * @return 마이프로필
     */
    Profile getMyProfile(Long profileId);

    /**
     * 마이프로필 생성
     * @param tokenClaimsDTO 멤버 식별자
     * @param request
     * @return
     */
    Profile createMyProfile(TokenDTO.tokenClaimsDTO tokenClaimsDTO, ProfileRequest.CreateProfileDTO request);

    Profile updateIsDefault(String email, Long profileId);

    Profile updateIsDefaultToFalse(String email, Long profileId);
    /**
     * 내 마이프로필 수정
     * @param tokenClaimsDTO 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return 수정된 마이프로필의 특징
     */
    ProfileFeature updateMyProfile(TokenDTO.tokenClaimsDTO tokenClaimsDTO, Long profileId, ProfileRequest.UpdateProfileDTO request);

    /**
     * 내 마이프로필 이미지 수정
     * @param tokenClaimsDTO 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param image 이미지
     * @param request
     * @return 수정된 마이프로필 이미지
     */
    ProfileImage updateMyProfileImage(TokenDTO.tokenClaimsDTO tokenClaimsDTO, Long profileId, MultipartFile image, ProfileRequest.UpdateProfileImageDTO request);

    /**
     * 내 마이프로필 삭제
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     */
    void deleteMyProfile(Long memberId, Long profileId);

    /**
     * 프로필 검색
     * @param serialNumber 시리얼 넘버
     * @return 검색된 프로필
     */
    Profile searchProfile(int serialNumber);
}
