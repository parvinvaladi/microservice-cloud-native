package com.programming.orderservice.service.impl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
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
        List<Long> bookId = orderItems.stream().map(
                orderItem -> orderItem.getBookId()
        ).collect(Collectors.toList());

        List<InventoryResponseDto> inventoryResponseDtos = webClient.get()
                .uri("http://localhost:8081/api/v1/inventory/is-in-stock?bookId={bookId}",bookId.toArray())
                .retrieve()
                .toEntityList(InventoryResponseDto.class)
                .block().getBody();

        boolean allBooksInStock = inventoryResponseDtos.stream().allMatch(InventoryResponseDto::isInStock);

            if (allBooksInStock){
                orderRepository.save(order);
//                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return ResponseEntity.ok("");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not all books in stock");
            }

    }
}
