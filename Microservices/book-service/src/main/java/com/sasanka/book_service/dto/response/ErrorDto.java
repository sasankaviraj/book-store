package com.sasanka.book_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int statusCode;
    private String errorMessage;
}
