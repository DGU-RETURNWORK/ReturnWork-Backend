package com.example.dgu.returnwork.domain.region.controller;

import com.example.dgu.returnwork.domain.region.dto.response.SearchRegionDto;
import com.example.dgu.returnwork.global.dto.PageResponseDto;
import com.example.dgu.returnwork.global.exception.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Region", description = "지역 검색 API")
public interface RegionApi {

    /**
     * Searches regions by a keyword across all address levels and returns matching results.
     *
     * The search is case-insensitive and supports partial matches (e.g., "서울", "종로", "청운동").
     *
     * @param searchKeyword the keyword to search for in addresses (city/province, district, neighborhood, etc.)
     * @param page zero-based page index to retrieve; defaults to 0 when not specified
     * @return a paged response containing a list of matching SearchRegionDto entries and pagination metadata
     */
    @Operation(
            summary = "지역 검색",
            description = """
                    키워드로 지역을 검색하는 API입니다.
                    - 시/도, 시/군/구, 동/리 등 모든 주소 레벨에서 검색 가능
                    - 대소문자 구분 없이 검색
                    - 부분 검색 지원 (예: "서울", "종로", "청운동" 모두 검색 가능)
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "지역 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.example.dgu.returnwork.global.response.ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                                    {
                                        "errorCode": null,
                                        "message": "OK",
                                        "result": {
                                            "content": [
                                                {
                                                    "id": 1,
                                                    "fullAddress": "서울특별시 종로구 청운동"
                                                },
                                                {
                                                    "id": 2,
                                                    "fullAddress": "서울특별시 종로구 신교동"
                                                }
                                            ],
                                            "currentPage": 0,
                                            "totalPage": 5,
                                            "totalElements": 45,
                                            "hasNext": true
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "검증 실패",
                                    summary = "검색 키워드가 비어있는 경우",
                                    value = """
                                    {
                                        "status": 400,
                                        "errorCode": "COMMON_002",
                                        "message": "입력값 검증에 실패했습니다"
                                    }
                                    """
                            )
                    )
            )
    })
    PageResponseDto<SearchRegionDto> searchRegion(
            @Parameter(
                    description = "검색할 지역 키워드 (시/도, 시/군/구, 동/리 등)",
                    example = "서울"
            )
            @RequestParam String searchKeyword,

            @RequestParam(defaultValue = "0") Integer page
    );
}