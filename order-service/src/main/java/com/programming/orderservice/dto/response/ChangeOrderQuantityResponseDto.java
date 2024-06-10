package com.programming.orderservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ChangeOrderQuantityResponseDto {
    private Long id;
    private Long bookId;
    private Integer newQuantity;
    private String statusOfOrder;
    private Long customerId;
    private Boolean isSuccess;
}
