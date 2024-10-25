package com.sasanka.bookstore.controller;

import com.sasanka.bookstore.dto.AuthorDto;
import com.sasanka.bookstore.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.sasanka.bookstore.util.Constants.BASE_URL_AUTHOR;
@RestController
@RequestMapping(BASE_URL_AUTHOR)
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody AuthorDto authorDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        authorDto.setToken(token);
        return authorService.save(authorDto);
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody AuthorDto authorDto, @PathVariable("id") Long id){
        return authorService.update(authorDto, id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete( @PathVariable("id") Long id){
        return authorService.delete(id);
    }

    @GetMapping(value = "/get/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByName( @PathVariable("name") String name){
        return authorService.getByName(name);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        return authorService.getAll();
    }
}
