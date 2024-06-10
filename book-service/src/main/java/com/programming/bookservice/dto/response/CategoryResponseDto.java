package com.programming.bookservice.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponseDto(
        Long id,
        String name,
        String persianName
) {
}
