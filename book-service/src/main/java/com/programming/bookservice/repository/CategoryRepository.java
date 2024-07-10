package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

//    @Query("select c from Category c JOIN FETCH c.books where c.id=:id")  /////////// for solving n+1 problem
    @Query("select c from Category c where c.id=:id")
    Optional<Category> findByIdWithBook(Long id);

}
