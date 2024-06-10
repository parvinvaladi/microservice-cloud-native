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
    public Book save(Book book) {
//        entityManager.persist(book);
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Book", Book.class).getResultList();
    }

    @Override
    public Book getById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Book where id = :id", Book.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public String findCategoryName(Long bookId) {
        Session currentSession = entityManager.unwrap(Session.class);
//        return currentSession.createQuery("from Book b inner join b.category c where b.id = :bookId", String.class).setParameter("bookId", bookId).getSingleResult();
        return currentSession.createQuery("SELECT c.name from Book b inner join b.category c where b.id = :bookId ", String.class).setParameter("bookId", bookId).getSingleResult();
    }

    @Override
    public List<Book> findAllByCategoryId(Long categoryId) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Book where category.id = :categoryId", Book.class).setParameter("categoryId", categoryId).getResultList();
    }
}
