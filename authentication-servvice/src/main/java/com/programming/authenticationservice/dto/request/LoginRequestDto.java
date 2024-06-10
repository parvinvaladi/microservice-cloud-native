package com.programming.authenticationservice.dto.request;

public record LoginRequestDto(
        String userName,
        String password
) {
}
