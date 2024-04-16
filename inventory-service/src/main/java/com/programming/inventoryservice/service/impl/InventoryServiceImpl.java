package com.programming.inventoryservice.service.impl;

import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.mapper.InventoryMapper;
import com.programming.inventoryservice.model.Inventory;
import com.programming.inventoryservice.repository.InventoryRepository;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.service.InventoryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public List<InventoryResponseDto> isInStock(List<Long> bookId) {
//        log.info("wait started");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        log.info("wait ended");
        return inventoryRepository.findAllById(bookId).stream().map(
                inventory -> InventoryResponseDto.builder()
                        .bookId(inventory.getPid())
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

    @Override
    public List<InventoryDto> getAll(Integer pageSize, Integer pageNumber) {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryDto> inventoryDtoList = inventoryMapper.toDtos(inventoryList);
        return inventoryDtoList;
    }
}
