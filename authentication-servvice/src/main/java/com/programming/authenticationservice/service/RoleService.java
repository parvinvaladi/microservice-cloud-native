package com.programming.authenticationservice.service;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<ResponseMessageDto> saveRole(String name);
}
