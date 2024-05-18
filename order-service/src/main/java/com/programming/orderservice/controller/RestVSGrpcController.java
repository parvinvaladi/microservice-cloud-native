package com.programming.orderservice.controller;


import com.programming.orderservice.IsInStockGRPCResponse;
import com.programming.orderservice.dto.request.OrderItemRequestDto;
import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.dto.response.InventoryResponseDto;
import com.programming.orderservice.grpc.GRPCIsInStockService;
import com.programming.orderservice.repository.OrderItemRepository;
import com.programming.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restVSgrpc")
public class RestVSGrpcController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GRPCIsInStockService grpcIsInStockService;

    @PostMapping("/rest/save-order/v1")
    public ResponseEntity saveOrder(@RequestBody OrderItemRequestDto requestDto){
        return ResponseEntity.ok(orderService.saveOrder(requestDto));
    }

    @GetMapping("/grpc/save-order/v1")
    public ResponseEntity<InventoryResponseDto> saveOrderGrpc(@RequestParam List<Long> bookIds, @RequestParam List<Integer> quantities){

        IsInStockGRPCResponse grpcIsInStockServiceInStock = grpcIsInStockService.isInStock(bookIds, quantities);
        InventoryResponseDto inventoryResponseDto = InventoryResponseDto.builder()
                .bookIds(grpcIsInStockServiceInStock.getBookIdsList())
                .isInStock(grpcIsInStockServiceInStock.getIsInStockList())
                .build();
        return ResponseEntity.ok(inventoryResponseDto);
    }
}
