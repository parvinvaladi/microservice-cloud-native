package com.programming.orderservice.projection;


//// interface based projection to fetch just specific properties of an entity from database
public interface OrderItemProjection {

    Long getBookId();
}
