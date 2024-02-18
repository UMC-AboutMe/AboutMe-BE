package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.ProfileFeature;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileFeatureRepository extends JpaRepository<ProfileFeature, Long> {
    List<ProfileFeature> findByProfileKeyAndProfileValueContaining(String profileKey, String profileValue);

    @Query("select pf.profileValue " +
            "from Profile p " +
            "join ProfileFeature pf on p = pf.profile " +
            "where pf.profileKey = 'name' and p.member = :member " +
            "order by p.isDefault desc, p.createdAt asc ")
    List<String> findProfileFeature(@Param("member") Member member);
}
