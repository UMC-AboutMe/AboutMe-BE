package com.example.aboutme.repository;


import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.mapping.MemberSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberSpaceRepository extends JpaRepository<MemberSpace, Long> {
    MemberSpace findByMemberAndSpace(Member member, Space space);

    List<MemberSpace> findByMemberAndSpace_NicknameContaining(Member member, String keyword);

    @Query("select count(*)" +
            "from MemberSpace ms join Space s on ms.space = s " +
            "where s.member = :member")
    Integer countSharedProfileByMember(@Param("member") Member member);
}
