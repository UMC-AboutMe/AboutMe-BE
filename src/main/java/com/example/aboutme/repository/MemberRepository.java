package com.example.aboutme.repository;

import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndSocial(String email, Social social);

    Boolean existsByEmailAndSocial(String email, Social social);
}