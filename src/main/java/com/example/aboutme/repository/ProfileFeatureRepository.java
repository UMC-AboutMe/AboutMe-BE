package com.example.aboutme.repository;

import com.example.aboutme.domain.ProfileFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileFeatureRepository extends JpaRepository<ProfileFeature, Long> {
    List<ProfileFeature> findByProfileKeyAndProfileValueContaining(String profileKey, String profileValue);
}
