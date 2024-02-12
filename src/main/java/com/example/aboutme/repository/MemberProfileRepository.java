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

    @Query("select count(*)" +
            "from MemberProfile mp join Profile as p on mp.profile = p " +
            "where p.member = :member")
    Integer countSharedProfileByMember(@Param("member") Member member);
}
