package com.example.dgu.returnwork.domain.region.controller;

import com.example.dgu.returnwork.domain.region.dto.response.SearchRegionDto;
import com.example.dgu.returnwork.domain.region.service.RegionQueryService;
import com.example.dgu.returnwork.global.dto.PageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController implements RegionApi {

    private final RegionQueryService regionQueryService;

    /**
     * Searches regions matching the provided keyword and returns a paginated result.
     *
     * @param searchKeyword keyword used to filter regions
     * @param page zero-based page index (defaults to 0)
     * @return a page of SearchRegionDto objects with pagination metadata
     */
    @Override
    @GetMapping
    public PageResponseDto<SearchRegionDto> searchRegion (@RequestParam String searchKeyword,
                                                          @RequestParam(defaultValue = "0") Integer page) {
        return regionQueryService.searchRegion(searchKeyword, page);
    }
}