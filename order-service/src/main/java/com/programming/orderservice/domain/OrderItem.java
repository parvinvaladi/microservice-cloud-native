package com.programming.orderservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ORDER_SERVICE_ORDER_ITEMS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

//    ,generator = "ORDER_SERVICE_ORDER_ITEMS_SEQ"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "QUANTITY")
    private Integer quantity;

//    pending, shipped, delivered, canceled)
    private String status;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "FK_ORDER")
    private Order order;
}
