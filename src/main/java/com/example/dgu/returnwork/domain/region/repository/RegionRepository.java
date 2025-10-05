package com.example.dgu.returnwork.domain.region.repository;

import com.example.dgu.returnwork.domain.region.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE LOWER(r.searchKeywords) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Region> searchByKeywordIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}
