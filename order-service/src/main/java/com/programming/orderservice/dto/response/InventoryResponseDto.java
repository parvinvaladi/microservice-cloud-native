package com.programming.orderservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record InventoryResponseDto(
        List<Long> bookIds,
        List<Boolean> isInStock
) {
}
