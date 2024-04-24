package com.programming.bookservice.service.impl;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.dto.request.BookRequestDto;
import com.programming.bookservice.dto.response.BookResponseDto;
import com.programming.bookservice.repository.BookRepository;
import com.programming.bookservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveProduct(BookRequestDto requestDto) {
        log.info(String.valueOf(requestDto));
        Book product = Book.builder()
                .name(requestDto.name())
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

//        try {
//            if(!TYPE.equals(file.getContentType())){
//                return "please upload excel file";
//            }
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        return null;
    }
}
