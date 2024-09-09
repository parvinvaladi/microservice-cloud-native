package com.programming.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Practice {

    @GetMapping(value = "/practice")
    public ResponseEntity practice(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
