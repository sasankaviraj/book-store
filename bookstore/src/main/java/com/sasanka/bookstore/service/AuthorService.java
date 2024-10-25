package com.sasanka.bookstore.service;

import com.sasanka.bookstore.dto.AuthorDto;
import org.springframework.http.ResponseEntity;

public interface AuthorService {
    ResponseEntity<?> save(AuthorDto authorDto);
    ResponseEntity<?> update(AuthorDto authorDto, Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> getByName(String name);
    ResponseEntity<?> getAll();
}
