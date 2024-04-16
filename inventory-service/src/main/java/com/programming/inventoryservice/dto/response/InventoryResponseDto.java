package com.programming.inventoryservice.dto.response;

import lombok.Builder;

@Builder
public record InventoryResponseDto(
        Long bookId,
        boolean isInStock
) {
}
