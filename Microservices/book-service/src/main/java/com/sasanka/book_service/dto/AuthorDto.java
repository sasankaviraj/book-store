package com.sasanka.book_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDto {
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    private String bio;
    private String token;
    private List<BookDto> bookDtos = new ArrayList<>();
}
