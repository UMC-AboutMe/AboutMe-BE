package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.mapping.MemberSpace;
import com.example.aboutme.service.MemberService.MemberService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Space findByMember_Id(Long memberId);
    boolean existsByMember(Member member);
    boolean existsByNickname(String nickname);
    Optional<Space> findByMember(Member member);

    Optional<Space> findByNickname(String keyword);
}
