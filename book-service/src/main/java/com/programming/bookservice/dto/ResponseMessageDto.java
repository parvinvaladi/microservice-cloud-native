package com.programming.bookservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseMessageDto {
    private String message;
    private HttpStatus status;
    private Object data;
}
