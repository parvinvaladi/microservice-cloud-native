package com.programming.authenticationservice.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ValidationErrorResponse {
    private String fieldName;
    private String message;
}
