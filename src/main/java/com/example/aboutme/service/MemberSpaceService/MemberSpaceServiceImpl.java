package com.example.aboutme.service.MemberSpaceService;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.repository.MemberSpaceRepository;
import com.example.aboutme.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSpaceServiceImpl implements MemberSpaceService {

    private final MemberSpaceRepository memberSpaceRepository;
    private final SpaceRepository spaceRepository;
    private final MemberRepository memberRepository;

    // 아지트 내 스페이스 목록 조회
    public List<MemberSpace> filterWithKeyword(Long memberId, String keyword) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return memberSpaceRepository.findByMemberAndSpace_NicknameContaining(member, keyword);
    }

    // 아지트 내 스페이스 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long memberId, Long spaceId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId).get();

        MemberSpace memberSpace = memberSpaceRepository.findByMemberAndSpace(member, space);
        if (memberSpace == null) {
            throw new GeneralException(ErrorStatus.MEMBER_SPACE_NOT_FOUND);
        }

        memberSpace.toggleFavorite();

        return memberSpace.isFavorite();
    }

    // 아지트 내 스페이스 추가
    @Transactional
    public MemberSpace addMemberSpace(Long memberId, Long spaceId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId).get();

        if (space.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.CANNOT_SHARE_OWN_SPACE);
        }

        if (memberSpaceRepository.findByMemberAndSpace(member, space) != null) {
            throw new GeneralException(ErrorStatus.MEMBER_SPACE_ALREADY_EXISTING);
        }

        MemberSpace memberSpace = MemberSpace.builder()
                .member(member)
                .space(space)
                .favorite(false)
                .build();

        memberSpaceRepository.save(memberSpace);

        return memberSpace;
    }


    // 아지트 내 스페이스 삭제
    @Transactional
    public void deleteMemberSpace(Long memberId, Long spaceId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId).get();

        MemberSpace memberSpaceToDelete = memberSpaceRepository.findByMemberAndSpace(member, space);
        if (memberSpaceToDelete == null) {
            throw new GeneralException(ErrorStatus.MEMBER_SPACE_NOT_FOUND);
        }

        memberSpaceRepository.delete(memberSpaceToDelete);
    }
}
