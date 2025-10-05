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

    /**
     * Retrieve the Region with the given identifier.
     *
     * @param regionId the identifier of the region to retrieve
     * @return the Region matching the provided identifier
     * @throws BaseException if no region exists for the given identifier (RegionErrorCode.REGION_NOT_FOUND)
     */
    public Region findRegionById(Long regionId) {
        return regionRepository.findById(regionId)
                .orElseThrow(() -> BaseException.type(RegionErrorCode.REGION_NOT_FOUND));
    }

    /**
     * Searches for regions whose properties match the given keyword (case-insensitive) and returns paginated results.
     *
     * @param keyword the search term to match against regions; leading and trailing whitespace is ignored
     * @param page zero-based page index to return
     * @return a page of SearchRegionDto representing matching regions for the requested page
     */
    public PageResponseDto<SearchRegionDto> searchRegion(String keyword, Integer page){

        String trimKeyword = keyword.trim();

        Pageable pageable = PageRequest.of(page, 10);

        return PageResponseDto.from(regionRepository.searchByKeywordIgnoreCase(trimKeyword, pageable)
                .map(SearchRegionDto::from));
    }
}