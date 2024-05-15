package com.programming.orderservice.service;

import com.programming.orderservice.dto.request.OrderItemRequestDto;
import com.programming.orderservice.dto.request.OrderRequestDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<String> saveOrder(OrderItemRequestDto requestDto);
}
