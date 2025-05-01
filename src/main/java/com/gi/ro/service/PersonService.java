package com.gi.ro.service;

import com.gi.ro.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonService<T extends Person> {
    T save(T person);

    Optional<T> findById(UUID id);

    List<T> findAll();

    T update(UUID id, T person);

    void deleteById(UUID id);
}