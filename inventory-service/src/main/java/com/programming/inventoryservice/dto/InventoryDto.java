package com.programming.inventoryservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class InventoryDto {
    private Long pid;

    @NotNull
    private Long bookId;

    @NotNull
    private Integer quantity;
}
