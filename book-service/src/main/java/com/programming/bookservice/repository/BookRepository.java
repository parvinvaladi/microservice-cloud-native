package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> getById(Long id);

    List<Book> findAllByCategoryId(Long categoryId);
    String findCategoryName(Long bookId);

    Book uploadImage(byte[] image);
}
