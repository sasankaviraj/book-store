package com.sasanka.bookstore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min = 6)
    private String password;
}
