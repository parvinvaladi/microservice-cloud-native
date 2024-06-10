package com.programming.inventoryservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record IsInStockResponseDto(
        List<Long> bookIds,
        List<Boolean> isInStock
) {
}
