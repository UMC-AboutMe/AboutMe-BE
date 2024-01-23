package com.example.aboutme.service;

import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.constant.Social;
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
@RequiredArgsConstructor
public class MemberSpaceService {

    private final MemberSpaceRepository memberSpaceRepository;
    private final SpaceRepository spaceRepository;
    private final MemberRepository memberRepository;

    // 아지트 내 스페이스 목록 조회
    @Transactional
    public MemberSpaceResponse filterWithKeyword(String keyword) {
        // Member authMember = getAuthenticatedMember();

        // 임시로 DB에 있는 1번 멤버 조회
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Member with id 1 not found"));

        List<MemberSpace> memberSpaces = memberSpaceRepository.findByMember(member);

        List<String> filteredNicknames = memberSpaces.stream()
                .map(MemberSpace::getSpace)
                .map(Space::getNickname)
                .filter(nickname -> nickname.contains(keyword))
                .toList();

            List<Space> filteredSpaces = spaceRepository.findByNicknameIn(filteredNicknames);
            List<MemberSpace> filteredMemberSpaces = memberSpaceRepository.findBySpaceIn(filteredSpaces);

        List<MemberSpaceResponse.ListDto> memberSpaceListDto = filteredMemberSpaces.stream()
                .map(memberSpace -> MemberSpaceResponse.ListDto.builder()
                        .spaceId(memberSpace.getSpace().getId())
                        .nickname(memberSpace.getSpace().getNickname())
                        .characterType(memberSpace.getSpace().getCharacterType())
                        .roomType(memberSpace.getSpace().getRoomType())
                        .favorite(memberSpace.isFavorite())
                        .build())
                .toList();

        return new MemberSpaceResponse(memberSpaceListDto);
    }

    // 아지트 내 스페이스 즐겨찾기
    @Transactional
    public Boolean toggleFavorite(Long spaceId) {
        // Member authMember = getAuthenticatedMember();

        // 임시로 DB에 있는 1번 멤버 조회
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Member with id 1 not found"));

        MemberSpace memberSpace = memberSpaceRepository.findByMemberAndSpaceId(member, spaceId);
        memberSpace.toggleFavorite();

        return memberSpace.isFavorite();
    }

    // 아지트 내 스페이스 추가
    @Transactional
    public void addMemberSpace(Long spaceId){
        // Member authMember = getAuthenticatedMember();

        // 임시로 DB에 있는 1번 멤버 조회
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Member with id 1 not found"));

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new NoSuchElementException("Space with id " + spaceId + " not found"));

        MemberSpace memberSpace = MemberSpace.builder()
                .member(member)
                .space(space)
                .favorite(false)
                .build();

        memberSpaceRepository.save(memberSpace);
    }


    // 아지트 내 스페이스 삭제
    @Transactional
    public void deleteMemberSpace(Long spaceId){
        // Member authMember = getAuthenticatedMember();

        // 임시로 DB에 있는 1번 멤버 조회
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Member with id 1 not found"));

        MemberSpace MemberSpaceToDelete = memberSpaceRepository.findByMemberAndSpaceId(member, spaceId);
        memberSpaceRepository.delete(MemberSpaceToDelete);
    }


//    private Member getAuthenticatedMember() {
//        Long memberId = Auth...;
//        return memberRepository.findById(memberId)
//    }

}
