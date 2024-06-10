package com.programming.authenticationservice.controller;

import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.service.CapService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class CapController {

    private final CapService capService;

    public CapController(CapService capService) {
        this.capService = capService;
    }

    @GetMapping("/kapcha")
    public ResponseEntity<ResponseMessageDto> get(HttpServletRequest request){
        return capService.get(request);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ResponseMessageDto> evaluate(HttpServletRequest request,String text){
        return capService.evaluate(text, request);
    }
}
