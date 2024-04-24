package com.programming.bookservice.service;

import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    void saveProduct(BookRequestDto requestDto);
    List<BookResponseDto> getAll();
    String upload(MultipartFile file);
}
