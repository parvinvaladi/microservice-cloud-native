package com.programming.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;

import java.util.List;

@Builder
public record InventoryRequestDto(
        List<Long> bookIds,
        List<Integer> quantities
) {
}
