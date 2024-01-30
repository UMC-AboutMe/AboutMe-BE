package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.service.MemberService.MemberService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Space findByMember_Id(Long memberId);
    List<Space> findByNicknameIn(List<String> nicknames);
}
