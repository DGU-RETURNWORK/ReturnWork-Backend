package com.example.dgu.returnwork.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponseDto<T>(
        List<T> content,

        int currentPage,

        int totalPage,

        long totalElements,

        boolean hasNext
) {
    /**
     * Create a PageResponseDto from a Spring Data Page.
     *
     * @param page the Spring Data Page to convert
     * @param <T> the element type of the page content
     * @return a PageResponseDto containing the page's content, the page number as currentPage,
     *         the total number of pages as totalPage, the total number of elements, and whether a next page exists
     */
    public static <T> PageResponseDto<T> from(Page<T> page) {
        return new PageResponseDto<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext()
        );
    }
}