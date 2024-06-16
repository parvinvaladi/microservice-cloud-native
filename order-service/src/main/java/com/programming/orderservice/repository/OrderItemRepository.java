package com.programming.orderservice.repository;

import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.dto.response.OrderItemResponseProjectionDto;
import com.programming.orderservice.projection.OrderItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>, JpaSpecificationExecutor<OrderItem> {

    //// interface based projection to fetch just specific properties of an entity from database
    @Query("select o from OrderItem o")
    List<OrderItemProjection> findAllProjections();

    //// record based projection to fetch just specific properties of an entity from database
    @Query("select new com.programming.orderservice.dto.response.OrderItemResponseProjectionDto(o.bookId) from OrderItem o")
    List<OrderItemResponseProjectionDto> findAllProjectionsByDto();
}
