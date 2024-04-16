package com.programming.inventoryservice.dto;

public record InventoryDto(
        Long pid,
        String bookName,
        Integer quantity
) {
}
