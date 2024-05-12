package com.programming.bookservice.repository.impl;

import com.programming.bookservice.domain.Category;
import com.programming.bookservice.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Category> findAll() {
        Session session = entityManager.unwrap(Session.class);
        List<Category> categories = session.createQuery("FROM Category",Category.class).getResultList();
        return categories;
    }
}
