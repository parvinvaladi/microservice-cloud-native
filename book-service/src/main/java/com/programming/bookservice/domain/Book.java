package com.programming.bookservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book_service_book")
@Data
public class Book {

//    ,generator = "PRODUCT_SERVICE_SEQ"
    @Id
    @Column(name = "PID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME",unique = true, nullable = false)
    private String name;

    @Column(name = "PUBLISHER_NAME")
    private String publisherName;

    @Column(name = "PUBLISH_DATE")
    @Temporal(value = TemporalType.DATE)
    private Date publishDate;

    @Column(name = "AUTHOR_NAME")
    private String authorName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "FK_BOOK_CATEGORY")
    private Category category;
}
