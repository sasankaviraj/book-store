package com.sasanka.bookstore.service;

import com.sasanka.bookstore.dto.LoginDto;
import com.sasanka.bookstore.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserManagementService {

    ResponseEntity<?> register(UserDto userDto);
    ResponseEntity<?> login(LoginDto loginDto);
}
