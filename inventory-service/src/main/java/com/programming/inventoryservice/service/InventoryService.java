package com.programming.inventoryservice.service;

import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    List<InventoryDto> isInStock(List<Long> skuCode);
    Void saveToInventory(InventoryDto requestDto);
    List<InventoryDto> getAll(Integer pageSize, Integer pageNumber);
}
