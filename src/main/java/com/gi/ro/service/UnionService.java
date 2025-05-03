package com.gi.ro.service;

import com.gi.ro.entity.Union;
import com.gi.ro.service.dto.UnionCreateDTO;
import com.gi.ro.service.dto.UnionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnionService {

    Union save(UnionCreateDTO union);

    Optional<Union> findById(UUID id);

    List<Union> findAll();

    Union update(UnionDTO union);

    void deleteById(UUID id);
}
