package com.example.dgu.returnwork.domain.region.service;

import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.region.dto.response.SearchRegionDto;
import com.example.dgu.returnwork.domain.region.exception.RegionErrorCode;
import com.example.dgu.returnwork.domain.region.repository.RegionRepository;
import com.example.dgu.returnwork.global.dto.PageResponseDto;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionQueryService {
    private final RegionRepository regionRepository;

    public Region findRegionById(Long regionId) {
        return regionRepository.findById(regionId)
                .orElseThrow(() -> BaseException.type(RegionErrorCode.REGION_NOT_FOUND));
    }

    public PageResponseDto<SearchRegionDto> searchRegion(String keyword, Integer page){

        String trimKeyword = keyword.trim();

        Pageable pageable = PageRequest.of(page, 10);

        return PageResponseDto.from(regionRepository.searchByKeywordIgnoreCase(trimKeyword, pageable)
                .map(SearchRegionDto::from));
    }
}
