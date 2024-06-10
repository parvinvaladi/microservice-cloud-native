package com.programming.inventoryservice.common;

import lombok.Data;

@Data
public class ErrorDto {
    private String message;
    private int status;

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
