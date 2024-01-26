package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findBySerialNumber(int serialNumber);

    int countByMember(Member member);

    List<Profile> findAllByMemberOrderByIsDefaultDesc(Member member);
}
