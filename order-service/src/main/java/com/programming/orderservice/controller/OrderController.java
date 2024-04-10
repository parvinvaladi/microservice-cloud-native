package com.programming.orderservice.controller;

import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "زخیره سفارش")
//    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
    public CompletableFuture<String> saveOrder(@RequestBody OrderRequestDto requestDto){
        return CompletableFuture.supplyAsync(()-> orderService.saveOrder(requestDto));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequestDto requestDto,RuntimeException exception){
        return CompletableFuture.supplyAsync(()-> "something went wrong, please order after some time.");
    }
}
