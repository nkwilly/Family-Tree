package com.gi.ro.service.impl;

import com.gi.ro.entity.Male;
import com.gi.ro.repository.MaleRepository;
import com.gi.ro.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaleServiceImpl implements PersonService<Male> {

    private final MaleRepository maleRepository;

    @Override
    public Male save(Male male) {
        return maleRepository.save(male);
    }

    @Override
    public Optional<Male> findById(UUID id) {
        return maleRepository.findById(id);
    }

    @Override
    public List<Male> findAll() {
        return maleRepository.findAll();
    }

    @Override
    public Male update(UUID id, Male male) {
        if (maleRepository.existsById(id)) {
            male.setId(id);
            return maleRepository.save(male);
        }
        throw new IllegalArgumentException("Male not found");
    }

    @Override
    public void deleteById(UUID id) {
        maleRepository.deleteById(id);
    }
}