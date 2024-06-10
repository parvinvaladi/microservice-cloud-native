package com.programming.inventoryservice.dto.request;

import jakarta.persistence.Column;

public record SaveToInventoryRequestDto(
         String bookName,
         Integer quantity
) {
}
