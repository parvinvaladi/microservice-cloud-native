package com.programming.inventoryservice.common;

import com.programming.inventoryservice.dto.InventoryDto;
import lombok.Data;

import java.util.List;

@Data
public class InventoryPageDto {
    private int totalPage;
    private long totalRecords;
    private List<InventoryDto> content;
}
