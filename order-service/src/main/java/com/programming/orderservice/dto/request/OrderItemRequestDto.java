package com.programming.orderservice.dto.request;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long id,
        Long skuCode,
        BigDecimal price,
        Integer quantity
) {
}
