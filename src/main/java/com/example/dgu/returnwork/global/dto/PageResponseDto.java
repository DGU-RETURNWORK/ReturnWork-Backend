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
