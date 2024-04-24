package com.programming.orderservice.dto.request;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long id,
        Long bookId,
        BigDecimal price,
        Integer quantity
) {
}
