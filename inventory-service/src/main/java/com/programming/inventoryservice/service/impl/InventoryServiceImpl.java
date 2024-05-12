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

        List<Inventory> inventoryList = inventoryRepository.findAllById(requestDto.bookIds());
        List<Boolean> isInStockList = new ArrayList<>();
        //  add a check for quantity
        for (Inventory inventory : inventoryList){
            if (inventory.getQuantity() < requestDto.quantities().get(inventoryList.indexOf(inventory))){
                isInStockList.add(false);
            } else
                isInStockList.add(true);
        }
        IsInStockResponseDto responseDto = IsInStockResponseDto.builder()
                .bookIds(inventoryList.stream().map(Inventory::getPid).collect(Collectors.toList()))
                .bookNames(inventoryList.stream().map(Inventory::getBookName).collect(Collectors.toList()))
                .isInStock(isInStockList)
                .build();
        return responseDto;
    }

    @Override
    public ResponseEntity saveToInventory(InventoryDto requestDto) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByBookName(requestDto.bookName());
        if (inventoryOptional.isPresent()){
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + requestDto.quantity());
            inventoryRepository.save(inventory);
            return ResponseEntity.status(200).body(requestDto);
        }else {
            Inventory inventory = inventoryMapper.toEntity(requestDto);
            inventoryRepository.save(inventory);
        }
        return ResponseEntity.status(201).body(requestDto);
    }

    @Override
    public InventoryPageDto getAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Inventory> inventoryList = inventoryRepository.findAll(pageable);
        return inventoryMapper.toPageDto(inventoryList);
    }
}
