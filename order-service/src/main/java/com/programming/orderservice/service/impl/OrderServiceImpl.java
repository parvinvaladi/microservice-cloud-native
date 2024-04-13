package com.programming.orderservice.service.impl;

import com.programming.orderservice.dto.request.OrderRequestDto;
import com.programming.orderservice.event.OrderPlacedEvent;
import com.programming.orderservice.repository.OrderRepository;
import com.programming.orderservice.dto.response.InventoryResponseDto;
import com.programming.orderservice.domain.Order;
import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.service.OrderService;
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
    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate kafkaTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder, KafkaTemplate kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String saveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems= orderRequestDto.orderItemRequestDtos().stream().map(orderItemRequestDto ->
                        OrderItem.builder()
                                .skuCode(orderItemRequestDto.skuCode())
                                .price(orderItemRequestDto.price())
                                .quantity(orderItemRequestDto.quantity())
                                .build()
                ).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        List<String> skuCodes = orderItems.stream().map(
                orderItem -> orderItem.getSkuCode()
        ).collect(Collectors.toList());

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

        return "";

    }
}
