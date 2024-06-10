package com.programming.bookservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

@AllArgsConstructor
public class SaveBookResponseDto {
    private Long bookId;
    private String name;
}
