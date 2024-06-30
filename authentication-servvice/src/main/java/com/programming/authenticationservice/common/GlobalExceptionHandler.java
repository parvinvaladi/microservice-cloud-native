package com.programming.authenticationservice.common;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
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
