package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
    Book getById(Long id);

    List<Book> findAllByCategoryId(Long categoryId);
    String findCategoryName(Long bookId);
}
