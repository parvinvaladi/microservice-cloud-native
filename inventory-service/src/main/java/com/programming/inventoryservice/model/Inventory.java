package com.programming.inventoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_INVENTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID")
    private Long pid;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "QUANTITY")
    private Integer quantity;
}
