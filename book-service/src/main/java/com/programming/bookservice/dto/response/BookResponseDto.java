package com.programming.bookservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        String categoryName
) {
}
