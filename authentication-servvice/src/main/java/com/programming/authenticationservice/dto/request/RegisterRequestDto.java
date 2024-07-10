package com.programming.authenticationservice.dto.request;

import com.programming.authenticationservice.common.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@PasswordMatches
public record RegisterRequestDto(
        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String firstName,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String lastName,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String gender,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String userName,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String password,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        String matchingPassword,

        @NotNull(message = "this field must not be null")
        @NotEmpty(message = "this field must not be empty")
        @Email
        String email
) {
}
