package com.example.dgu.returnwork.domain.region.dto.response;

import com.example.dgu.returnwork.domain.region.Region;

public record SearchRegionDto(
        Long id,

        String fullAddress
) {
    public static SearchRegionDto from(Region region) {
        return new SearchRegionDto(region.getId(), region.getFullAddress());
    }
}
