package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.mapping.MemberProfile;
import com.example.aboutme.domain.mapping.MemberSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    MemberProfile findByMemberAndProfileId(Member member, Long profileId);

}
