package com.example.aboutme.service.ProfileService;

import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    /**
     * 내 마이프로필 목록 조회
     * @param memberId 멤버 식별자
     * @return 마이프로필 목록
     */
    List<Profile> getMyProfiles(Long memberId);

    /**
     * 내 마이프로필 단건 조회
     * @param memberId 멤버 식별자
     * @param profileId 프로필 식별자
     * @return 마이프로필
     */
    Profile getMyProfile(Long memberId, Long profileId);

    /**
     * 마이프로필 생성
     * @param memberId 멤버 식별자
     * @param request
     * @return
     */
    Profile createMyProfile(Long memberId, ProfileRequest.CreateProfileDTO request);

    Profile updateIsDefault(Long memberID, Long profileId);

    /**
     * 내 마이프로필 수정
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param request
     * @return 수정된 마이프로필의 특징
     */
    ProfileFeature updateMyProfile(Long memberId, Long profileId, ProfileRequest.UpdateProfileDTO request);

    /**
     * 내 마이프로필 이미지 수정
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     * @param image 이미지
     * @param request
     * @return 수정된 마이프로필 이미지
     */
    ProfileImage updateMyProfileImage(Long memberId, Long profileId, MultipartFile image, ProfileRequest.UpdateProfileImageDTO request);

    /**
     * 내 마이프로필 삭제
     * @param memberId 멤버 식별자
     * @param profileId 마이프로필 식별자
     */
    void deleteMyProfile(Long memberId, Long profileId);

    Profile searchProfile(int serialNumber);
}
