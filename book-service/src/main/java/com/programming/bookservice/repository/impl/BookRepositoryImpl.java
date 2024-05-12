package com.programming.bookservice.repository.impl;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.repository.BookRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private EntityManager entityManager;
    @Override
    public void save(Book book) {
//        entityManager.persist(book);
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(book);
    }

    @Override
    public List<Book> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Book", Book.class).getResultList();
    }
}
