package com.example.dgu.returnwork.domain.region.service;

import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.region.exception.RegionErrorCode;
import com.example.dgu.returnwork.domain.region.repository.RegionRepository;
import com.example.dgu.returnwork.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionQueryService {
    private final RegionRepository regionRepository;

    public Region findRegionByName(String regionName) {
        return regionRepository.findByName(regionName)
                .orElseThrow(() -> BaseException.type(RegionErrorCode.REGION_NOT_FOUND));
    }
}
