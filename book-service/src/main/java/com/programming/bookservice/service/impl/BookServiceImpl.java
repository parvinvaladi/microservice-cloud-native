package com.programming.bookservice.service.impl;

import com.programming.bookservice.common.ExcelUtility;
import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;
import com.programming.bookservice.dto.ResponseMessageDto;
import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.dto.response.CategoryResponseDto;
import com.programming.bookservice.repository.BookRepository;
import com.programming.bookservice.repository.CategoryRepository;
import com.programming.bookservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ResponseMessageDto> saveProduct(BookRequestDto requestDto) {
        log.info(String.valueOf(requestDto));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date;
        try {
            date = formatter.parse(requestDto.publishDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Optional<Category> category = categoryRepository.findById(requestDto.categoryId());
        if (category.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder().status(HttpStatus.NOT_FOUND)
                    .message(HttpStatus.NOT_FOUND.name())
                    .build());
        Book product = Book.builder()
                .name(requestDto.name())
                .publisherName(requestDto.publisherName())
                .publishDate(date)
                .description(requestDto.description())
                .price(requestDto.price())
                .category(category.get())
                .build();
        Book saved = bookRepository.save(product);
//        SaveBookResponseDto responseDto = new SaveBookResponseDto(saved.getId(), saved.getName());
//        return responseDto;
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessageDto.builder()
                        .data(saved.getId())
                .build());
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
    public ResponseEntity<ResponseMessageDto> getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.getById(id);
        if (bookOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder().status(HttpStatus.NOT_FOUND)
                    .message("not found")
                    .build());
        Book book = bookOptional.get();
        BookResponseDto responseDto = BookResponseDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .price(book.getPrice())
                .categoryId(book.getCategory().getId())
                .categoryName(bookRepository.findCategoryName(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessageDto.builder()
                        .data(responseDto)
                .build());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> getBooksByCategory(Long categoryId) {
        Optional<Category> categoryById = categoryRepository.findById(categoryId);
        if (categoryById.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder().status(HttpStatus.NOT_FOUND)
                    .message(HttpStatus.NOT_FOUND.name())
                    .build());
        List<Book> products = bookRepository.findAllByCategoryId(categoryId);
        List<BookResponseDto> responseDtos = products.stream().map(product -> BookResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(categoryById.get().getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessageDto.builder()
                .data(responseDtos)
                .build());
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
            ExcelUtility.setCategoryRepository(categoryRepository);
            List<Book> bookList = ExcelUtility.excelToBookList(file.getInputStream());
            List<Book> books = bookRepository.findAll();
            bookList.forEach(bookRepository::save);
            message = "The Excel file is uploaded: " + file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Please upload a valid Excel file!");
        }
        return message;
    }

    @Override
    @Cacheable(value = "cache")
    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .persianName(category.getPersianName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> uploadImage(MultipartFile file, Long bookId) {
        Optional<Book> bookOptional;
        byte [] byteImage;
        try {
            byteImage = file.getBytes();
            bookOptional = bookRepository.getById(bookId);
            if (bookOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder().status(HttpStatus.NOT_FOUND)
                                .message(HttpStatus.NOT_FOUND.name())
                        .build());
            log.info(Arrays.toString(byteImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Book book = bookOptional.get();
        book.setImage(byteImage);
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessageDto.builder()
                        .status(HttpStatus.NOT_FOUND)
                .build());
    }
}
