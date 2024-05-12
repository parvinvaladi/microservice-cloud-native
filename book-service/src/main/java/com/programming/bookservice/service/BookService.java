package com.programming.bookservice.service;

import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.dto.response.CategoryResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    String saveProduct(BookRequestDto requestDto);
    List<BookResponseDto> getAll();
    String upload(MultipartFile file);
    List<CategoryResponseDto> getCategories();
}
