package com.programming.inventoryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IsInStockRequestDto(

        @NotNull
        @NotEmpty
        List<Long> bookIds,

        @NotNull
        @NotEmpty
        List<Integer> quantities
) {
}
