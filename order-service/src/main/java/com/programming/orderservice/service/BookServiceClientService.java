package com.programming.orderservice.service;

import com.programming.orderservice.dto.ResponseMessageDto;
import com.programming.orderservice.dto.response.BookResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service", url = "http://localhost:8083/api/v1/book")
public interface BookServiceClientService {

    @GetMapping("/book-by-id")
    ResponseEntity<BookResponseDto> getById(@RequestParam Long id);
}
