package com.programming.bookservice.common;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class BookItemReader implements ItemReader<Book> {

    @Autowired
    private BookRepository bookRepository;

    private Iterator<Book> bookIterator;
    @Override
    public Book read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (bookIterator == null){
            log.info("reader");
            List<Book> bookList = bookRepository.findAll();
            bookIterator = bookList.iterator();
        }
        if (bookIterator.hasNext()) {
            log.info("reader");
            return bookIterator.next();
        } else {
            return null;
        }
    }
}

























