package com.programming.bookservice.repository;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;

import java.util.List;

public interface CategoryRepository {

    public List<Category> findAll();
    Category findById(Long id);
}
