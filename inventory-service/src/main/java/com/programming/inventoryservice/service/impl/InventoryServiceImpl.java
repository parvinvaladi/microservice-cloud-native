package com.programming.inventoryservice.service.impl;

import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.model.Inventory;
import com.programming.inventoryservice.repository.InventoryRepository;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    @SneakyThrows
    public List<InventoryResponseDto> isInStock(List<String> bookName) {
        log.info("wait started");
        Thread.sleep(10000);
        log.info("wait ended");
        return inventoryRepository.findByBookNameIn(bookName).stream().map(
                inventory -> InventoryResponseDto.builder()
                        .skuCode(inventory.getBookName())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public Void saveToInventory(SaveToInventoryRequestDto requestDto) {
        Inventory inventory = Inventory.builder()
                .bookName(requestDto.bookName())
                .quantity(requestDto.quantity())
                .build();
        inventoryRepository.save(inventory);
        return null;
    }
}
