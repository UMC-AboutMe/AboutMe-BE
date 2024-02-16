package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.AlarmConverter;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.repository.*;
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
    private final AlarmRepository alarmRepository;

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

    public List<MemberProfile> getMyProfilesStorage(Long memberId) {
        Member member = memberService.findMember(memberId);
        return memberProfileRepository.findAllByMember(member);
    }

    @Transactional
    public MemberProfile deleteMemberProfile(Long memberId, Long profileId) {
        Member member = memberService.findMember(memberId);
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROFILE_NOT_FOUND));
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
     *
     * @param memberId 멤버 식별자
     * @param request
     */
    @Transactional
    public void addOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request) {

        Member member = memberService.findMember(memberId);

        // 추가하려는 마이프로필 목록 조회
        List<Profile> otherProfileList = request.getProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        for (Profile otherProfile : otherProfileList) {
            // 이미 공유된 프로필일 경우
            if (memberProfileRepository.existsByMemberAndProfile(member, otherProfile)) {
                throw new GeneralException(ErrorStatus.MEMBER_PROFILE_ALREADY_EXIST);
            }
            // 본인의 프로필을 추가하려는 경우
            if (otherProfile.getMember() == member) {
                throw new GeneralException(ErrorStatus.CANNOT_SHARE_OWN_PROFILE);
            }
        }

        List<MemberProfile> memberProfileList = otherProfileList.stream()
                .map(otherProfile -> MemberProfileConverter.toMemberProfile(member, otherProfile))
                .toList();

        memberProfileList.forEach(memberProfile -> {
            memberProfileRepository.save(memberProfile);
        });
    }

    /**
     * 프로필 보관함 내 검색하기
     *
     * @param memberId 멤버 식별자
     * @param keyword  검색어(프로필 이름)
     */
    public List<MemberProfile> filterWithKeyword(Long memberId, String keyword) {

        Member member = memberService.findMember(memberId);

        List<ProfileFeature> profileFeatureList = profileFeatureRepository.findByProfileKeyAndProfileValueContaining("name", keyword);

        List<Profile> profileList = profileFeatureList.stream()
                .map(ProfileFeature::getProfile)
                .toList();

        return memberProfileRepository.findByMemberAndProfileIn(member, profileList);
    }

    /**
     * 마이프로필 공유 -> 알람데이터 생성
     *
     * @param memberId 멤버 식별자
     * @param request
     */
    @Transactional
    public void sendMyProfile(Long memberId, ProfileRequest.SendProfileDTO request) {

        Member member = memberService.findMember(memberId);

        // 상대방 마이프로필 목록 조회
        List<Profile> otherProfileList = request.getOthersProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        for (Profile otherProfile : otherProfileList) {
            // 상대방 프로필 일련번호란에 본인 프로필 일련번호를 기입한 경우
            if (otherProfile.getMember() == member) {
                throw new GeneralException(ErrorStatus._BAD_REQUEST);
            }
        }

        Member targetMember = otherProfileList.get(0).getMember();

        // 공유할 마이프로필 목록 조회
        List<Profile> myProfileList = request.getMyProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        for (Profile myProfile : myProfileList) {
            // 이미 공유된 프로필일 경우
            if (memberProfileRepository.existsByMemberAndProfile(targetMember, myProfile)) {
                throw new GeneralException(ErrorStatus.MEMBER_PROFILE_ALREADY_EXIST);
            }
            // 이미 생성된 알람인 경우
            if (alarmRepository.existsByMemberAndProfile(targetMember, myProfile)) {
                throw new GeneralException(ErrorStatus.ALARM_ALREADY_EXISTING);
            }
            // 본인의 프로필이 아닌 경우
            if (myProfile.getMember() != member) {
                throw new GeneralException(ErrorStatus.PROFILE_NOT_MINE);
            }
        }

        List<Alarm> alarmList = myProfileList.stream()
                .map(myProfile -> {
                    String profileValue = myProfile.getProfileFeatureList().stream()
                            .filter(profileFeature -> profileFeature.getProfileKey().equals("name"))
                            .map(ProfileFeature::getProfileValue).findFirst().orElse(null);
                    return AlarmConverter.toProfileAlarm(targetMember, myProfile, profileValue);
                })
                .toList();

        alarmList.forEach(alarm -> {
            alarmRepository.save(alarm);
        });
    }
}
