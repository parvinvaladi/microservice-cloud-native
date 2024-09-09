package com.programming.orderservice.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Practice {

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @GetMapping(value = "/practice")
    public ResponseEntity practice(){
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity rateLimitFallback(Throwable t) {
        return new ResponseEntity(HttpStatus.TOO_MANY_REQUESTS);
    }
}
