package com.programming.orderservice.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class OrderItemResponseDto {

    private Long id;

    private Long bookId;

    private BigDecimal price;

    private Integer quantity;

    private String status;

    private Long customerId;
    private Long categoryId;
    private String categoryName;
}
