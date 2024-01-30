package com.example.aboutme.service.MemberProfileService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.ProfileRequest;
import com.example.aboutme.converter.MemberProfileConverter;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.repository.MemberProfileRepository;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MemberService memberService;

    // 프로필 보관함 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long memberId, Long profileId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROFILE_NOT_FOUND));

        MemberProfile memberProfile = memberProfileRepository.findByMemberAndProfile(member, profile);
        if (memberProfile == null) {
            throw new GeneralException(ErrorStatus.MEMBER_PROFILE_NOT_FOUND);
        }

        memberProfile.toggleFavorite();

        return memberProfile.getFavorite();
    }

    @Transactional
    public void AddOthersProfilesAtMyStorage(Long memberId, ProfileRequest.ShareProfileDTO request){

        Member member = memberService.findMember(memberId);

        // 추가하려는 마이프로필 목록 불러오기
        List<Profile> otherProfileList = request.getProfileSerialNumberList().stream()
                .map(serialNum -> profileRepository.findBySerialNumber(serialNum).get())
                .toList();

        for(Profile otherProfile : otherProfileList){
            if(memberProfileRepository.existsByMemberAndProfile(member, otherProfile)){
                throw new GeneralException(ErrorStatus.MEMBER_PROFILE_ALREADY_EXIST);
            }
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
    }
}
