package com.programming.inventoryservice.dto.request;

import java.util.List;

public record IsInStockRequestDto(
        List<Long> bookIds,
        List<Integer> quantities
) {
}
