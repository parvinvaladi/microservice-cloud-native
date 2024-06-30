package com.programming.authenticationservice.controller;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import com.programming.authenticationservice.service.impl.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@CrossOrigin
public class UserController {

    private final MyUserDetailsService myUserDetailsService;

    public UserController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessageDto> register(@Valid @RequestBody RegisterRequestDto requestDto){
        return myUserDetailsService.register(requestDto);
    }

    @GetMapping("/get-user-details")
    public UserDetails getUserDetails(@RequestParam String userName){
        return myUserDetailsService.loadUserByUsername(userName);
    }

}
