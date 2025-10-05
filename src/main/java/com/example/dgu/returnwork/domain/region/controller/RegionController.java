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

    @Override
    @GetMapping
    public PageResponseDto<SearchRegionDto> searchRegion (@RequestParam String keyword,
                                                          @RequestParam(defaultValue = "0") Integer page) {
        return regionQueryService.searchRegion(keyword, page);
    }
}
