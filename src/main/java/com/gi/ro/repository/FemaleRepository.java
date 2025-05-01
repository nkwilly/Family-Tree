package com.gi.ro.repository;

import com.gi.ro.entity.Female;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FemaleRepository extends JpaRepository<Female, UUID> {
}
