package com.programming.bookservice.repository.impl;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;
import com.programming.bookservice.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Category> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        try {
            Category category = session.createQuery("FROM Category WHERE id = :id",Category.class).setParameter("id",id).getSingleResult();
            return Optional.of(category);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
}
