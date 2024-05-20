package com.programming.bookservice.controller;

import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.dto.response.CategoryResponseDto;
import com.programming.bookservice.dto.response.SaveBookResponseDto;
import com.programming.bookservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/book")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping(value = "/save-book")
    @Operation(summary = "ذخیره کتاب جدید")
    public ResponseEntity saveBook(@RequestBody BookRequestDto requestDto){
        return ResponseEntity.ok(bookService.saveProduct(requestDto));
    }

    @Operation(summary = "دریافت لیست کتاب ها")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponseDto>> getAll(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @Operation(summary = "دریافت کتاب با استفاده از id")
    @GetMapping("/book-by-id")
    public ResponseEntity<BookResponseDto> getBookById(@RequestParam Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "دریافت لیست کتاب ها براساس دسته بندی")
    @GetMapping("/books-by-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponseDto>> getBooksByCategory(@RequestParam Long categoryId){
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryId));
    }

    @PostMapping("/upload-and-save-books")
    @Operation(summary = "بارگذاری فایل اکسل و ذخیره کتاب ها")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.upload(file));
    }

    @GetMapping("/get-categories")
    @Operation(summary = "دریافت لیست دسته بندی ها",tags = {"category"})
    public ResponseEntity<List<CategoryResponseDto>> getCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getCategories());
    }
}
