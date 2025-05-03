package com.gi.ro.service.impl;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Union;
import com.gi.ro.repository.FemaleRepository;
import com.gi.ro.repository.MaleRepository;
import com.gi.ro.repository.UnionRepository;
import com.gi.ro.service.MapperService;
import com.gi.ro.service.UnionService;
import com.gi.ro.service.dto.UnionCreateDTO;
import com.gi.ro.service.dto.UnionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final UnionRepository unionRepository;
    private final MapperService mapperService;
    private final MaleRepository maleRepository;
    private final FemaleRepository femaleRepository;

    @Override
    @Transactional
    public Union save(UnionCreateDTO dto) throws IllegalArgumentException {
        if (dto == null) return null;
        Union union = mapperService.toUnion(dto);
        Union saved = unionRepository.save(union);
        Male male = saved.getHusband();
        male.setUnionId(saved.getId());
        maleRepository.save(male);
        Female female = saved.getWife();
        female.setUnionId(saved.getId());
        femaleRepository.save(female);
        return saved;
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
    public Union update(UnionDTO dto) throws IllegalArgumentException {
        if (dto == null) return null;
        Union union = mapperService.toUnion(dto);
        return unionRepository.save(union);
    }

    @Override
    public void deleteById(UUID id) {
        unionRepository.deleteById(id);
    }
}