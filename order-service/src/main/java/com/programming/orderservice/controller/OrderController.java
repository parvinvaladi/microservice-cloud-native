package com.programming.orderservice.controller;

import com.programming.orderservice.dto.ResponseMessageDto;
import com.programming.orderservice.dto.request.OrderItemRequestDto;
import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/add-to-cart")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "زخیره سفارش")
//    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
    public ResponseEntity<String> saveOrder(@RequestBody OrderItemRequestDto requestDto){
        return orderService.saveOrder(requestDto);
    }

//    public CompletableFuture<String> fallbackMethod(OrderRequestDto requestDto,RuntimeException exception){
//        return CompletableFuture.supplyAsync(()-> "something went wrong, please order after some time.");
//    }

    @GetMapping(value = "/get-all")
    @Operation(summary = "دریافت لیست سفارشات")
    public ResponseEntity<ResponseMessageDto> getAll(){
        return orderService.getAll();
    }















    @GetMapping("get-all-book-ids-ordered")
    public ResponseEntity<ResponseMessageDto> getAllBookIdsOrdered(){
       return orderService.getAllBookIdsOrdered();
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<ResponseMessageDto> remove(@RequestParam Long id){
        return orderService.remove(id);
    }

    @PostMapping(value = "/change-quantity")
    public ResponseEntity<ResponseMessageDto> changeQuantity(@RequestParam Long id, @RequestParam Integer quantity){
        return orderService.changeQuantity(id, quantity);
    }
}
