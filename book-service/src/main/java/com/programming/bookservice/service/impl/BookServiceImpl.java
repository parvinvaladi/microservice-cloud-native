package com.programming.bookservice.service.impl;

import com.programming.bookservice.common.ExcelUtility;
import com.programming.bookservice.domain.Book;
import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.repository.BookRepository;
import com.programming.bookservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveProduct(BookRequestDto requestDto) {
        log.info(String.valueOf(requestDto));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date;
        try {
            date = formatter.parse(requestDto.publishDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Book product = Book.builder()
                .name(requestDto.name())
                .publisherName(requestDto.publisherName())
                .publishDate(date)
                .description(requestDto.description())
                .price(requestDto.price())
                .build();

        log.info(String.valueOf(product));
        bookRepository.save(product);
    }

    @Override
    public List<BookResponseDto> getAll() {
        List<Book> products = bookRepository.findAll();
        List<BookResponseDto> responseDtos = products.stream().map(product -> BookResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()).collect(Collectors.toList());
        return responseDtos;
    }

    @Override
    public String upload(MultipartFile file) {
        log.info("upload started");
        String message = "";
        if (!ExcelUtility.hasExcelFormat(file)){
            message = "The Excel file is not upload: " + file.getOriginalFilename() + "!";
            return message;
        }

        try {
            List<Book> bookList = ExcelUtility.excelToBookList(file.getInputStream());
            List<Book> books = bookRepository.findAll();
            bookList.forEach(book -> {
                if (!books.stream().anyMatch(b -> b.getName().equals(book.getName()))){
                    bookRepository.save(book);
                }

            });
            message = "The Excel file is uploaded: " + file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Please upload a valid Excel file!");
        }
        return message;
    }
}
