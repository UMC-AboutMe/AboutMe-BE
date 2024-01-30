package com.example.aboutme.repository;

import com.example.aboutme.domain.ProfileFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileFeatureRepository extends JpaRepository<ProfileFeature, Long> {
}
