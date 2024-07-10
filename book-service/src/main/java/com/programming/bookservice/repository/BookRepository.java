package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("select b from Book b where b.category.id=:id")
    List<Book> findAllByCategoryId(@Param("id") Long categoryId);

    @Query("SELECT b.category.name from Book b where b.id=:bookId")
    String findCategoryName(Long bookId);

}
