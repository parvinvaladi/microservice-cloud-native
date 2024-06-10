package com.programming.inventoryservice.service;

import com.programming.inventoryservice.common.InventoryPageDto;
import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.IsInStockRequestDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.dto.response.IsInStockResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    IsInStockResponseDto isInStock(IsInStockRequestDto requestDto);
    ResponseEntity saveToInventory(InventoryDto requestDto);
    List<InventoryDto> getAll(Integer pageSize, Integer pageNumber);
}
