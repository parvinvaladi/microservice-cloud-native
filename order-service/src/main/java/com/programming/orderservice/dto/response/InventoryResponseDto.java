package com.programming.orderservice.dto.response;

import lombok.Builder;

@Builder
public record InventoryResponseDto(
        String skuCode,
        boolean isInStock
) {
}
