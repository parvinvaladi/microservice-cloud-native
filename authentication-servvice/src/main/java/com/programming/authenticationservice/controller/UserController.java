package com.programming.authenticationservice.controller;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import com.programming.authenticationservice.service.impl.MyUserDetailsService;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UserController {

    private final MyUserDetailsService myUserDetailsService;

    public UserController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessageDto> register(@RequestBody RegisterRequestDto requestDto){
        return myUserDetailsService.register(requestDto);
    }

    @PostMapping("/login")
    public UserDetails login(@RequestBody String userName){
        return myUserDetailsService.loadUserByUsername(userName);
    }

}
