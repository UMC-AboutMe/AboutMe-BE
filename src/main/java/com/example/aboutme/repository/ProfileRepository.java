package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.ProfileFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findBySerialNumber(int serialNumber);

    int countByMember(Member member);

    List<Profile> findAllByMemberOrderByIsDefaultDesc(Member member);

    boolean existsBySerialNumber(int serialNumber);

    Optional<Profile> findByMemberAndId(Member member, Long id);
}
