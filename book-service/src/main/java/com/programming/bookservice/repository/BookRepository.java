package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book,String>, JpaSpecificationExecutor<Book> {
}
