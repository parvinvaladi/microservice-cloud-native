package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    public List<Category> findAll();
    Optional<Category> findById(Long id);
}
