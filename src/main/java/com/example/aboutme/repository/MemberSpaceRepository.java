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

    /**
     * 내 마이스페이스 공유 현황 (내 마이스페이스가 상대방의 보관함에 얼마나 저장되었는지)
     * @param member 조회하려는 멤버
     * @return 공유된 마이스페이스 개수
     */
    @Query("select count(*)" +
            "from MemberSpace ms join Space s on ms.space = s " +
            "where s.member = :member")
    Integer countSharedSpaceByMember(@Param("member") Member member);
}
