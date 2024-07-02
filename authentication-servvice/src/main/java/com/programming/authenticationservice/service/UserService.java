package com.programming.authenticationservice.service;

import com.programming.authenticationservice.domain.User;
import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
     ResponseEntity<ResponseMessageDto> register(RegisterRequestDto requestDto);
}
