package com.gi.ro.service.impl;

import com.gi.ro.entity.Female;
import com.gi.ro.repository.FemaleRepository;
import com.gi.ro.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FemaleServiceImpl implements PersonService<Female> {

    private final FemaleRepository femaleRepository;

    @Override
    public Female save(Female female) {
        return femaleRepository.save(female);
    }

    @Override
    public Optional<Female> findById(UUID id) {
        return femaleRepository.findById(id);
    }

    @Override
    public List<Female> findAll() {
        return femaleRepository.findAll();
    }

    @Override
    public Female update(UUID id, Female female) {
        if (femaleRepository.existsById(id)) {
            female.setId(id);
            return femaleRepository.save(female);
        }
        throw new IllegalArgumentException("Female not found");
    }

    @Override
    public void deleteById(UUID id) {
        femaleRepository.deleteById(id);
    }
}