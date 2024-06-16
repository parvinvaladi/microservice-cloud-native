package com.programming.bookservice.service;

import com.programming.bookservice.domain.Category;
import com.programming.bookservice.dto.ResponseMessageDto;
import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.dto.response.CategoryResponseDto;
import com.programming.bookservice.dto.response.SaveBookResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

//    SaveBookResponseDto saveProduct(BookRequestDto requestDto);
    ResponseEntity<ResponseMessageDto> saveProduct(BookRequestDto requestDto);
    List<BookResponseDto> getAll();
    ResponseEntity<ResponseMessageDto> getBooksByCategory(Long categoryId);
    ResponseEntity<ResponseMessageDto> upload(MultipartFile file);
    List<CategoryResponseDto> getCategories();

    ResponseEntity<ResponseMessageDto> getBookById(Long id);

    ResponseEntity<ResponseMessageDto> uploadImage(MultipartFile file,Long bookId);
}
