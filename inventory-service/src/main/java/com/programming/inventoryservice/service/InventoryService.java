package com.programming.inventoryservice.service;

import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDto> isInStock(List<String> skuCode);
    Void saveToInventory(SaveToInventoryRequestDto requestDto);
}
