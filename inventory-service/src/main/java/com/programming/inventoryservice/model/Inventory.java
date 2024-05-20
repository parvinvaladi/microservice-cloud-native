package com.programming.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "inventory_service_inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID")
    private Long pid;

    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_RESTOCKED_DATE")
    private Date lastRestockedDate;

    @Column(name = "LOCATION")
    private String location;
}
