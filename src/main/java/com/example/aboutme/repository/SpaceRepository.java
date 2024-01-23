package com.example.aboutme.repository;

import com.example.aboutme.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// 엔티티 자체 추가
public interface SpaceRepository extends JpaRepository<Space, Long> {
    List<Space> findByNicknameIn(List<String> nicknames);
}