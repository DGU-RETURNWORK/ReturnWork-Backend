package com.example.dgu.returnwork.domain.region.repository;

import com.example.dgu.returnwork.domain.region.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    /**
     * Searches for regions whose `searchKeywords` contain the given term, matching case-insensitively, and returns results according to the provided pagination.
     *
     * @param keyword  the substring to match against Region.searchKeywords (case-insensitive)
     * @param pageable pagination and sorting information for the query results
     * @return a Page of Region entities whose searchKeywords contain `keyword`, respecting the supplied Pageable
     */
    @Query("SELECT r FROM Region r WHERE LOWER(r.searchKeywords) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Region> searchByKeywordIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}