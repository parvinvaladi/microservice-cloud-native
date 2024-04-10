package com.programming.orderservice.repository;

import com.programming.orderservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>, JpaSpecificationExecutor<OrderItem> {
}
