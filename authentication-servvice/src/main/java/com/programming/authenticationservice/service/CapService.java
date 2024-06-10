package com.programming.authenticationservice.service;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CapService {
    ResponseEntity<ResponseMessageDto> get(HttpServletRequest request);
    ResponseEntity<ResponseMessageDto> evaluate(String text,HttpServletRequest request);
}
