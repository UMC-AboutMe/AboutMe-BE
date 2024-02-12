package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.mapping.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    List<MemberProfile> findAllByMember(Member member);

//    MemberProfile findByMemberAndId(Member member, Long memberProfileId);

    MemberProfile findByMemberAndProfile(Member member, Profile profile);
    Boolean existsByMemberAndProfile(Member member, Profile profile);
    List<MemberProfile> findByMemberAndProfileIn(Member member, List<Profile> profiles);

    /**
     * 내 마이프로필 공유 현황 (내 마이프로필이 상대방의 보관함에 얼마나 저장되었는지)
     * @param member 조회하려는 멤버
     * @return 공유된 마이프로필 개수
     */
    @Query("select count(*)" +
            "from MemberProfile mp join Profile as p on mp.profile = p " +
            "where p.member = :member")
    Integer countSharedProfileByMember(@Param("member") Member member);
}
