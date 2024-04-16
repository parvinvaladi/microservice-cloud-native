package com.programming.orderservice.service.impl;

import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.event.OrderPlacedEvent;
import com.programming.orderservice.repository.OrderRepository;
import com.programming.orderservice.dto.response.InventoryResponseDto;
import com.programming.orderservice.domain.Order;
import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
                                .bookId(orderItemRequestDto.skuCode())
                                .price(orderItemRequestDto.price())
                                .quantity(orderItemRequestDto.quantity())
                                .build()
                ).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        List<String> bookIds = orderItems.stream().map(
                orderItem -> orderItem.getBookId()
        ).collect(Collectors.toList());

        InventoryResponseDto[] inventoryResponseDtos = webClient.get()
                .uri("http://localhost:8081/v1/api/inventory",bookIds)
                .retrieve()
                .bodyToMono(InventoryResponseDto[].class)
                .block();

//        Span callInventoryService = tracer.nextSpan().name("callInventoryService");
//
//        try(Tracer.SpanInScope spanInScope = tracer.withSpan(callInventoryService.start())){
//            InventoryResponseDto[] inventoryResponseDtos = webClientBuilder.build().get()
//                    .uri("http://inventory-service/api/inventory",
//                            uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
//                    .retrieve()
//                    .bodyToMono(InventoryResponseDto[].class)
//                    .block();
//
//            boolean allProductInStock = Arrays.stream(inventoryResponseDtos).allMatch(InventoryResponseDto::isInStock);
//            if (allProductInStock){
//                orderRepository.save(order);
//                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
//                return "order replaced successfully";
//            }else {
//                throw new IllegalArgumentException("product is not in stock");
//            }
//        }finally {
//            callInventoryService.end();
//        }

        return ResponseEntity.ok("");

    }
}
