package com.programming.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record OrderPlacedEvent(
         String orderNumber
) {
}
