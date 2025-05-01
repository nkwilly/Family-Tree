package com.gi.ro.repository;

import com.gi.ro.entity.Male;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaleRepository extends JpaRepository<Male, UUID> {
}
