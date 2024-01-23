package com.example.aboutme.repository;


import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.mapping.MemberSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberSpaceRepository extends JpaRepository<MemberSpace, Long> {
    MemberSpace findByMemberAndSpaceId(Member member, Long spaceId);

    List<MemberSpace> findByMember(Member member);

    List<MemberSpace> findBySpaceIn(List<Space> spaces);
}
