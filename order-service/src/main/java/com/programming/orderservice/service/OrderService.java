package com.programming.orderservice.service;

import com.programming.orderservice.dto.request.OrderRequestDto;

public interface OrderService {
    String saveOrder(OrderRequestDto requestDto);
}
