package com.programming.authenticationservice.controller;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@CrossOrigin
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("save-role")
    @Operation(summary = "",tags = "")
    public ResponseEntity<ResponseMessageDto> save(@RequestParam String name){
        return roleService.saveRole(name);
    }
}
