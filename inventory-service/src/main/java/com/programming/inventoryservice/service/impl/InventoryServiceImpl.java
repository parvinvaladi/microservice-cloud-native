package com.programming.inventoryservice.service.impl;

import com.programming.inventoryservice.common.InventoryPageDto;
import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.IsInStockRequestDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.IsInStockResponseDto;
import com.programming.inventoryservice.exception.BookNotFoundException;
import com.programming.inventoryservice.mapper.InventoryMapper;
import com.programming.inventoryservice.model.Inventory;
import com.programming.inventoryservice.repository.InventoryRepository;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.service.InventoryService;
import jakarta.ws.rs.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public IsInStockResponseDto isInStock(IsInStockRequestDto requestDto) {

        List<Inventory> inventoryList = new ArrayList<>();
        List<Boolean> isInStockList = new ArrayList<>();

        for (Long bookId : requestDto.bookIds()) {
            Optional<Inventory> byBookId = inventoryRepository.findByBookId(bookId);
            if (byBookId.isPresent()) {
                Inventory inventory = byBookId.get();
                inventoryList.add(inventory);

                int quantityIndex = requestDto.bookIds().indexOf(bookId);
                int requestedQuantity = requestDto.quantities().get(quantityIndex);

                if (inventory.getQuantity() >= requestedQuantity) {
                    isInStockList.add(true);
                } else {
                    isInStockList.add(false);
                }
            }
        }

        IsInStockResponseDto responseDto = IsInStockResponseDto.builder()
                .bookIds(inventoryList.stream().map(Inventory::getBookId).collect(Collectors.toList()))
                .isInStock(isInStockList)
                .build();

        return responseDto;
    }

    @Override
    public ResponseEntity saveToInventory(InventoryDto requestDto) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByBookId(requestDto.getBookId());
        if (inventoryOptional.isPresent()){
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + requestDto.getQuantity());
            inventoryRepository.save(inventory);
            return ResponseEntity.status(200).body(requestDto);
        }else {
            Inventory inventory = inventoryMapper.toEntity(requestDto);
            inventoryRepository.save(inventory);
        }
        return ResponseEntity.status(201).body(requestDto);
    }

    @Override
    public List<InventoryDto> getAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Inventory> inventoryList = inventoryRepository.findAll(pageable);
        return inventoryMapper.inventoryPageToDtoList(inventoryList);
    }
}
