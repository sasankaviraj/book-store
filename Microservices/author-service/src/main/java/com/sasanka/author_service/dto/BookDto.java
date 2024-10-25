package com.sasanka.author_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    @NotEmpty(message = "Book name cannot be empty")
    private String bookName;
    @NotEmpty(message = "ISBN cannot be empty")
    private String isbn;
    @NotEmpty(message = "Author IDs cannot be empty")
    private List<Long> authorIds = new ArrayList<>();
    @NotNull(message = "Published date cannot be null")
    private LocalDate publishedDate;
    @NotNull(message = "Price cannot be null")
    private Double price;
}
