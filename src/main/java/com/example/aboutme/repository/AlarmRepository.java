package com.example.aboutme.repository;

import com.example.aboutme.domain.Alarm;
import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Boolean existsByMemberAndProfile(Member member, Profile profile);

}