package com.example.dgu.returnwork.domain.region.repository;

import com.example.dgu.returnwork.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByName(String name);
}
