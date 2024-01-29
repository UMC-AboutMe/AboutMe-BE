package com.example.aboutme.service.MemberSpaceService;

import com.example.aboutme.app.dto.MemberSpaceResponse;
import com.example.aboutme.domain.mapping.MemberSpace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberSpaceService {
    List<MemberSpace> filterWithKeyword(Long memberId, String keyword);

    Boolean toggleFavorite(Long memberId, Long spaceId);

    MemberSpace addMemberSpace(Long memberId, Long spaceId);

    void deleteMemberSpace(Long memberId, Long spaceId);
}
