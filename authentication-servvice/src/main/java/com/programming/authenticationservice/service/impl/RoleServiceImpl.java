package com.programming.authenticationservice.service.impl;

import com.programming.authenticationservice.domain.Role;
import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.repo.RoleRepository;
import com.programming.authenticationservice.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<ResponseMessageDto> saveRole(String name) {
        roleRepository.save(Role.builder().name(name).build());
        return ResponseEntity.ok(ResponseMessageDto.builder().build());
    }
}
