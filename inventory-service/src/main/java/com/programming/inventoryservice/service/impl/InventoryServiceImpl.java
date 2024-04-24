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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public List<InventoryDto> isInStock(List<Long> bookId) {
        log.info("wait started");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("wait ended");
        List<Inventory> inventoryList = inventoryRepository.findAllById(bookId);
        // TODO: 21.04.24 add a check for quantity
        List<InventoryDto> inventoryResponseDtoList = inventoryMapper.toDtos(inventoryList);
        return inventoryResponseDtoList;
    }

    @Override
    public Void saveToInventory(InventoryDto requestDto) {
        Inventory inventory = inventoryMapper.toEntity(requestDto);
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
