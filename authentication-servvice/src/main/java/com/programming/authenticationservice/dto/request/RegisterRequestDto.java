package com.programming.authenticationservice.dto.request;

public record RegisterRequestDto(
        String firstName,
        String lastName,
        String gender,
        String userName,
        String password
) {
}
