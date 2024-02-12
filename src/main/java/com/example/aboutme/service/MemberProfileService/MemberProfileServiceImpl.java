package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.repository.MemberProfileRepository;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.repository.ProfileFeatureRepository;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ProfileFeatureRepository profileFeatureRepository;
    private final MemberService memberService;

    // 프로필 보관함 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long memberId, Long profileId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Profile profile = profileRepository.findById(profileId).get();

        MemberProfile memberProfile = memberProfileRepository.findByMemberAndProfile(member, profile);
        if (memberProfile == null) {
            throw new GeneralException(ErrorStatus.MEMBER_PROFILE_NOT_FOUND);
        }

        memberProfile.toggleFavorite();

        return memberProfile.getFavorite();
    }

    public List<MemberProfile> getMyProfilesStorage(Long memberId){
        Member member = memberService.findMember(memberId);
        return memberProfileRepository.findAllByMemberAndApprovedIsTrue(member);
    }

    @Transactional
    public MemberProfile deleteMemberProfile(Long memberId, Long profileId){
        Member member = memberService.findMember(memberId);
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(()->new GeneralException(ErrorStatus.PROFILE_NOT_FOUND));
//        boolean isSame = profile.getMember().getId().equals(memberId);
//        if (!isSame){
//            throw new GeneralException(ErrorStatus.MEMBER_IS_NOT_PROFILE_CREATOR);
//        }
        MemberProfile memberProfile = memberProfileRepository.findByMemberAndProfile(member, profile);
        memberProfileRepository.delete(memberProfile);
        return memberProfile;
    }

    /**
     * 상대방 마이프로필 내 보관함에 추가하기
     * @param memberId 멤버 식별자
     * @param request
     */
    @Transactional
    public Long addOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request){

        Member member = memberService.findMember(memberId);

        // 추가하려는 마이프로필 목록 조회
        List<Profile> otherProfileList = request.getProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        Long profileOwnerId = otherProfileList.isEmpty() ? null : otherProfileList.get(0).getMember().getId();

        for(Profile otherProfile : otherProfileList){
            // 이미 공유된 프로필일 경우
            if(memberProfileRepository.existsByMemberAndProfile(member, otherProfile)){
                throw new GeneralException(ErrorStatus.MEMBER_PROFILE_ALREADY_EXIST);
            }
            // 본인의 프로필을 추가하려는 경우
            if(otherProfile.getMember() == member){
                throw new GeneralException(ErrorStatus.CANNOT_SHARE_OWN_PROFILE);
            }
        }

        List<MemberProfile> memberProfileList = otherProfileList.stream()
                .map(otherProfile -> MemberProfileConverter.toMemberProfile(member, otherProfile))
                .toList();

        memberProfileList.forEach(memberProfile -> {
            memberProfileRepository.save(memberProfile);
        });

        return profileOwnerId;
    }

    /**
     * 프로필 보관함 내 검색하기
     * @param memberId 멤버 식별자
     * @param keyword 검색어(프로필 이름)
     */
    public List<MemberProfile> filterWithKeyword(Long memberId, String keyword) {

        Member member = memberService.findMember(memberId);

        List<ProfileFeature> profileFeatureList = profileFeatureRepository.findByProfileKeyAndProfileValueContaining("name", keyword);

        List<Profile> profileList = profileFeatureList.stream()
                .map(ProfileFeature::getProfile)
                .toList();

        return memberProfileRepository.findByMemberAndProfileInAndApprovedIsTrue(member, profileList);
    }

    /**
     * 내 마이프로필 상대방에게 공유하기
     * @param memberId 멤버 식별자
     * @param request
     */
    @Transactional
    public void shareMyProfilesToOthers(Long memberId, ProfileRequest.ShareMyProfileDTO request){

        Member member = memberService.findMember(memberId);

        // 추가하려는 마이프로필 목록 조회
        List<Profile> otherProfileList = request.getProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        Member shareTarget = memberService.findMember(request.getMemberId());

        for(Profile otherProfile : otherProfileList){
            // 공유하려는 프로필이 본인게 아닌 경우
            if(otherProfile.getMember() != member){
                throw new GeneralException(ErrorStatus.PROFILE_NOT_MINE);
            }
            // 이미 공유된 프로필일 경우
            if(memberProfileRepository.existsByMemberAndProfile(shareTarget, otherProfile)){
                throw new GeneralException(ErrorStatus.MEMBER_PROFILE_ALREADY_EXIST);
            }
        }

        List<MemberProfile> memberProfileList = otherProfileList.stream()
                .map(otherProfile -> MemberProfileConverter.toMemberProfileNotApproved(shareTarget, otherProfile))
                .toList();

        memberProfileList.forEach(memberProfile -> {
            memberProfileRepository.save(memberProfile);
        });
    }
}
