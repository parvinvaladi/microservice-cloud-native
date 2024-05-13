package com.programming.bookservice.service;

import com.programming.bookservice.domain.Category;
import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.dto.response.CategoryResponseDto;
import com.programming.bookservice.dto.response.SaveBookResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

//    SaveBookResponseDto saveProduct(BookRequestDto requestDto);
    Long saveProduct(BookRequestDto requestDto);
    List<BookResponseDto> getAll();
    List<BookResponseDto> getBooksByCategory(Long categoryId);
    String upload(MultipartFile file);
    List<CategoryResponseDto> getCategories();
}
