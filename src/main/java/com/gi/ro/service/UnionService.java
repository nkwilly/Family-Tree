package com.gi.ro.service;

import com.gi.ro.entity.Union;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnionService {

    Union save(Union union);

    Optional<Union> findById(UUID id);

    List<Union> findAll();

    Union update(UUID id, Union union);

    void deleteById(UUID id);
}
