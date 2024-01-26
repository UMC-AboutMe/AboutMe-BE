package com.example.aboutme.service.SpaceService;

import com.example.aboutme.app.dto.SpaceRequest;
import com.example.aboutme.converter.SpaceConverter;
import com.example.aboutme.domain.Space;
import com.example.aboutme.repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    @Override
    public Space JoinSpace(SpaceRequest.JoinDTO request) {
        Space newSpace = SpaceConverter.toSpace(request);
        return spaceRepository.save(newSpace);
    }
}
