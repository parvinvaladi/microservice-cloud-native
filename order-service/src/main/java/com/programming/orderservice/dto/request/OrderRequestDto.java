package com.programming.orderservice.dto.request;

import java.util.List;

public record OrderRequestDto(
        List<OrderItemRequestDto> orderItemRequestDtos
) {
}
