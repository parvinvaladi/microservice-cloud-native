package com.programming.orderservice.service.impl;

import com.programming.orderservice.domain.Order;
import com.programming.orderservice.dto.ResponseMessageDto;
import com.programming.orderservice.dto.request.InventoryRequestDto;
import com.programming.orderservice.dto.request.OrderItemRequestDto;
import com.programming.orderservice.dto.response.*;
import com.programming.orderservice.projection.OrderItemProjection;
import com.programming.orderservice.repository.OrderItemRepository;
import com.programming.orderservice.repository.OrderRepository;
import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.service.BookServiceClientService;
import com.programming.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final BookServiceClientService bookServiceClientService;

    @Autowired
    private WebClient webClient;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, BookServiceClientService bookServiceClientService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookServiceClientService = bookServiceClientService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseMessageDto> saveOrder(OrderItemRequestDto orderRequestDto) {

        try {
            ResponseEntity<ResponseMessageDto> bookById = bookServiceClientService.getById(orderRequestDto.bookId());

            if (bookById.getStatusCode() == HttpStatus.NOT_FOUND || bookById.getBody().getData() == null){
                throw new Exception("not found");
            }
        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
                .bookIds(List.of(orderRequestDto.bookId()))
                .quantities(List.of(orderRequestDto.quantity()))
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
                OrderItem orderItem = OrderItem.builder()
                        .bookId(orderRequestDto.bookId())
                        .quantity(orderRequestDto.quantity())
                        .build();
                OrderItem saved = orderItemRepository.save(orderItem);
//                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseMessageDto.builder()
                                .status(HttpStatus.CREATED)
                                .message("saved")
                                .data(orderItem)
                                .build());
            }else {
                throw new Exception("not in stock");
            }

        }catch (Exception e){
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(ResponseMessageDto.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message("not found")
                    .build());
        }
//        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
//                .bookIds(List.of(orderRequestDto.bookId()))
//                .quantities(List.of(orderRequestDto.quantity()))
//                .build();
//
//        InventoryResponseDto inventoryResponseDto = webClient.post()
//                .uri("http://localhost:8081/api/v1/inventory/is-in-stock")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(inventoryRequestDto)
//                .retrieve()
//                .toEntity(InventoryResponseDto.class)
//                .block()
//                .getBody();
//
//        boolean allBooksInStock = !inventoryResponseDto.isInStock().contains(false);
//
//            if (allBooksInStock){
//                OrderItem orderItem = OrderItem.builder()
//                        .bookId(orderRequestDto.bookId())
//                        .quantity(orderRequestDto.quantity())
//                        .build();
//                orderItemRepository.save(orderItem);
////                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
//                return ResponseEntity.ok("");
//            }else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not all books in stock");
//            }

    }

    @Override
    public ResponseEntity<ResponseMessageDto> getAll() {
        List<OrderItem> orders = orderItemRepository.findAll();
        ResponseMessageDto bookResponseDto = null;
        for (OrderItem order : orders) {
            bookResponseDto =  bookServiceClientService.getById(order.getId()).getBody();
        }
        BookResponseDto finalBookResponseDto = (BookResponseDto) bookResponseDto.getData();
        List<OrderItemResponseDto> orderItemResponseDtos = orders.stream().map(order -> OrderItemResponseDto.builder()
                .id(order.getId())
                .bookId(order.getBookId())
                .quantity(order.getQuantity())
                .price(finalBookResponseDto.price())
                .status(order.getStatus())
                .customerId(order.getCustomerId())
                .categoryId(finalBookResponseDto.categoryId())
                .categoryName(finalBookResponseDto.categoryName())
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .build()).toList();
        return ResponseEntity.ok(ResponseMessageDto.builder()
                        .message(HttpStatus.OK.toString())
                        .data(orderItemResponseDtos)
                        .status(HttpStatus.OK)
                .build());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> getAllBookIdsOrdered() {
//        List<OrderItemProjection> allProjections = orderItemRepository.findAllProjections();
        List<OrderItemResponseProjectionDto> allProjections = orderItemRepository.findAllProjectionsByDto();
        return ResponseEntity.ok(ResponseMessageDto.builder()
                .message(HttpStatus.OK.toString())
                .data(allProjections)
                .status(HttpStatus.OK)
                .build());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> remove(Long id) {
        Optional<OrderItem> orderOptional = orderItemRepository.findById(id);
        if (orderOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder()                                .message(HttpStatus.NOT_FOUND.toString())
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        }
        OrderItem orderItem = orderOptional.get();
        orderItemRepository.deleteById(id);
        return ResponseEntity.ok(ResponseMessageDto.builder()
                .message(HttpStatus.OK.toString())
                .data(RemoveOrderResponseDto.builder()
                        .id(orderItem.getId())
                        .bookId(orderItem.getBookId())
                        .status(orderItem.getStatus())
                        .quantity(orderItem.getQuantity())
                        .customerId(orderItem.getCustomerId())
                        .bookId(orderItem.getBookId())
                        .build())
                .status(HttpStatus.OK)
                .build());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> changeQuantity(Long idOfOrderItem, Integer newQuantity) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(idOfOrderItem);
        if (orderItemOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder()                                .message(HttpStatus.NOT_FOUND.toString())                                .status(HttpStatus.NOT_FOUND)
                    .build());
        }
        OrderItem orderItem = orderItemOptional.get();
        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
                .bookIds(List.of(orderItem.getBookId()))
                .quantities(List.of(newQuantity))
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
            orderItem.setQuantity(newQuantity);
            orderItemRepository.save(orderItem);
            return ResponseEntity.ok(ResponseMessageDto.builder()
                    .message(HttpStatus.OK.toString())
                    .data(ChangeOrderQuantityResponseDto.builder()
                            .id(orderItem.getId())
                            .bookId(orderItem.getId())
                            .statusOfOrder(orderItem.getStatus())
                            .isSuccess(true)
                            .newQuantity(orderItem.getQuantity())
                            .customerId(orderItem.getCustomerId())
                            .bookId(orderItem.getBookId())
                            .build())
                    .status(HttpStatus.OK)
                    .build());
        }
        return ResponseEntity.ok(ResponseMessageDto.builder()
                .message("not enough books in stock")
                .status(HttpStatus.NOT_FOUND)
                .build());
    }
}
