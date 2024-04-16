package com.programming.inventoryservice.controller;

import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.dto.request.SaveToInventoryRequestDto;
import com.programming.inventoryservice.dto.response.InventoryResponseDto;
import com.programming.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping(value = "/save")
    @Operation(summary = "‌خیره در فهرست محصولات")
    public ResponseEntity saveToInventory(@RequestBody SaveToInventoryRequestDto requestDto){
        return ResponseEntity.ok(inventoryService.saveToInventory(requestDto));
    }

    @GetMapping(value = "/get-all")
    @Operation(summary = "دریافت لیست کتاب ها")
    ResponseEntity<List<InventoryDto>> getAll(@RequestParam Integer pageSize,@RequestParam Integer pageNumber){
        return ResponseEntity.ok(inventoryService.getAll(pageSize,pageNumber));
    }


    @GetMapping()
    @Operation(summary = "وجود یا عدم وجود در انبار")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InventoryResponseDto>> isInStock(@RequestParam List<Long> productId){
        return ResponseEntity.ok(inventoryService.isInStock(productId));
    }
}
