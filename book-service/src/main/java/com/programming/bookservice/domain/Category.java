package com.programming.bookservice.domain;

import com.programming.lib.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "book_service_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    @Id
    @Column(name = "PID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORY_NAME",unique = true, nullable = false)
    private String name;

    @Column(name = "PERSIAN_NAME",unique = true, nullable = false)
    private String persianName;

    @Lob
    private String description;

    @OneToMany(cascade = CascadeType.ALL,targetEntity = Book.class,mappedBy = "category")
    private List<Book> books;
}
