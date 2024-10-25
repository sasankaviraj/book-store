package com.sasanka.bookstore.controller;

import com.sasanka.bookstore.dto.LoginDto;
import com.sasanka.bookstore.dto.UserDto;
import com.sasanka.bookstore.service.UserManagementService;
import com.sasanka.bookstore.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.BASE_URL_AUTH)
public class UserController {
    @Autowired
    private UserManagementService userManagementService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto){
        return userManagementService.register(userDto);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        return userManagementService.login(loginDto);
    }
}
