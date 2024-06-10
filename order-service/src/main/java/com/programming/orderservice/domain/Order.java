package com.programming.orderservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "order_service_orders")
@Data
public class Order {

//    ,generator = "ORDER_SERVICE_SEQ"
    @Id
    @Column(name = "PID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL,targetEntity = OrderItem.class,mappedBy = "order")
    private List<OrderItem> orderItems;
}
