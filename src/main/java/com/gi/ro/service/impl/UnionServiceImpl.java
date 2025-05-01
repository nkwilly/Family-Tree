package com.gi.ro.service.impl;

import com.gi.ro.entity.Union;
import com.gi.ro.repository.UnionRepository;
import com.gi.ro.service.UnionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final UnionRepository unionRepository;

    @Override
    public Union save(Union union) {
        return unionRepository.save(union);
    }

    @Override
    public Optional<Union> findById(UUID id) {
        return unionRepository.findById(id);
    }

    @Override
    public List<Union> findAll() {
        return unionRepository.findAll();
    }

    @Override
    public Union update(UUID id, Union union) {
        if (unionRepository.existsById(id)) {
            union.setId(id);
            return unionRepository.save(union);
        }
        throw new IllegalArgumentException("Union not found");
    }

    @Override
    public void deleteById(UUID id) {
        unionRepository.deleteById(id);
    }
}