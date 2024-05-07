package com.programming.bookservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record BookRequestDto(
        @NotBlank(message = "the name of book can not be empty")
        String name,
         String publisherName,
         String publishDate,
         String authorName,
         String description,
         BigDecimal price,
        @NotBlank(message = "the quantity of book can not be empty")
        Integer quantity
) {
}
