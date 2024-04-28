package com.programming.orderservice.service.impl;

import com.programming.orderservice.dto.request.InventoryRequestDto;
import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.event.OrderPlacedEvent;
import com.programming.orderservice.repository.OrderRepository;
import com.programming.orderservice.dto.response.InventoryResponseDto;
import com.programming.orderservice.domain.Order;
import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.service.OrderService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    private final KafkaTemplate kafkaTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<String> saveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems= orderRequestDto.orderItemRequestDtos().stream().map(orderItemRequestDto ->
                        OrderItem.builder()
                                .bookId(orderItemRequestDto.bookId())
                                .price(orderItemRequestDto.price())
                                .quantity(orderItemRequestDto.quantity())
                                .build()
                ).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        List<Long> bookIds = orderItems.stream().map(
                orderItem -> orderItem.getBookId()
        ).collect(Collectors.toList());

        List<Integer> quantities = orderItems.stream().map(
                orderItem -> orderItem.getQuantity()
        ).collect(Collectors.toList());

        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
                .bookIds(bookIds)
                .quantities(quantities)
                .build();

        InventoryResponseDto inventoryResponseDto = webClient.post()
                .uri("http://localhost:8081/api/v1/inventory/is-in-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestDto)
                .retrieve()
                .toEntity(InventoryResponseDto.class)
                .block()
                .getBody();

        boolean allBooksInStock = !inventoryResponseDto.isInStock().contains(false);

            if (allBooksInStock){
                orderRepository.save(order);
//                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return ResponseEntity.ok("");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not all books in stock");
            }
    }
}
