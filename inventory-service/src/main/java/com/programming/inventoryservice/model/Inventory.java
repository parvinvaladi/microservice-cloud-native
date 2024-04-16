package com.programming.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

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
