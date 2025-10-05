package com.example.dgu.returnwork.domain.region.dto.response;

import com.example.dgu.returnwork.domain.region.Region;

public record SearchRegionDto(
        Long id,

        String fullAddress
) {
    /**
     * Create a SearchRegionDto from a Region domain object.
     *
     * @param region the Region to convert
     * @return a SearchRegionDto containing the region's id and full address
     */
    public static SearchRegionDto from(Region region) {
        return new SearchRegionDto(region.getId(), region.getFullAddress());
    }
}