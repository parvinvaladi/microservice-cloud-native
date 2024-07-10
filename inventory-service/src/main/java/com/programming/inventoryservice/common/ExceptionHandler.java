package com.programming.inventoryservice.common;

import com.programming.inventoryservice.dto.ResponseMessageDto;
import com.programming.inventoryservice.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBookNotFoundException(BookNotFoundException ex) {
        log.info("this book is not found");
        ErrorDto error = new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseMessageDto> handleValidationException(MethodArgumentNotValidException exception){
        var validationErrorResponse = ValidationErrorResponse.builder()
                .fieldName(exception.getBindingResult().getFieldError().getField())
                .message(exception.getBindingResult().getFieldError().getDefaultMessage())
                .build();
        return new ResponseEntity<>(ResponseMessageDto.builder()
                .data(validationErrorResponse)
                .status(HttpStatus.BAD_REQUEST)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
