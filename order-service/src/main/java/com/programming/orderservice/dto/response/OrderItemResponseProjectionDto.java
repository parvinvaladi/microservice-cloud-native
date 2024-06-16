package com.programming.orderservice.dto.response;

//////// record based projection to fetch just specific properties of an entity from database
public record OrderItemResponseProjectionDto(
        Long bookId
) {
}
