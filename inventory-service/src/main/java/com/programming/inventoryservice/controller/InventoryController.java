package com.programming.inventoryservice.controller;

import com.programming.inventoryservice.common.InventoryPageDto;
import com.programming.inventoryservice.dto.request.IsInStockRequestDto;
import com.programming.inventoryservice.dto.response.IsInStockResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")

public class InventoryController {
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);


    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping(value = "/update-inventory")
    @Operation(summary = "آپدیت تعداد کتاب موجودی")
    public ResponseEntity saveToInventory(@RequestBody InventoryDto requestDto){
        return inventoryService.saveToInventory(requestDto);
    }

    @GetMapping(value = "/get-all")
    @Operation(summary = "دریافت لیست کتاب ها")
    ResponseEntity<List<InventoryDto>> getAll(@RequestParam Integer pageSize, @RequestParam Integer pageNumber){
        return ResponseEntity.ok(inventoryService.getAll(pageSize,pageNumber));
    }


    @PostMapping(value = "/is-in-stock")
    @Operation(summary = "وجود یا عدم وجود در انبار")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<IsInStockResponseDto> isInStock(@RequestBody IsInStockRequestDto requestDto, HttpServletRequest request){
        log.info(String.valueOf(request));
        return ResponseEntity.ok(inventoryService.isInStock(requestDto));
    }
}
