package com.programming.inventoryservice.dto.response;

import lombok.Builder;

@Builder
public record InventoryResponseDto(
        String skuCode,
        boolean isInStock
) {
}
