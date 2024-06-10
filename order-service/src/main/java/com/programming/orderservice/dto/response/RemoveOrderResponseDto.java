package com.programming.orderservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Setter
@Getter
public class RemoveOrderResponseDto {
    private Long id;
    private Long bookId;
    private Integer quantity;
    private String status;
    private Long customerId;
}
