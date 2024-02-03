package com.example.aboutme.repository;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Plan;
import com.example.aboutme.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

}
