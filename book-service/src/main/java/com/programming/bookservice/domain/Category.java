package com.programming.bookservice.domain;

import com.programming.bookservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "book_service_category")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
