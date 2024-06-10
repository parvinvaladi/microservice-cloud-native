package com.programming.inventoryservice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryDto {
    private Long pid;
    private Long bookId;
    private Integer quantity;
}
