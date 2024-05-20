package com.programming.orderservice.service;

import com.programming.orderservice.dto.ResponseMessageDto;
import com.programming.orderservice.dto.request.OrderItemRequestDto;
import com.programming.orderservice.dto.request.OrderRequestDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<String> saveOrder(OrderItemRequestDto requestDto);
    ResponseEntity<ResponseMessageDto> getAll();

    ResponseEntity<ResponseMessageDto> changeQuantity(Long id, Integer quantity);
    ResponseEntity<ResponseMessageDto> remove(Long id);
}
