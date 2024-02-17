package com.example.aboutme.repository;

import com.example.aboutme.app.dto.MyPageResponse;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.constant.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndSocial(String email, Social social);
    Optional<Member> findByEmail(String email);
    void deleteByEmail(String email);
    Boolean existsByEmailAndSocial(String email, Social social);
}
