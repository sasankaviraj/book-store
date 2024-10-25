package com.sasanka.author_service.service;

import com.sasanka.author_service.dto.AuthorDto;
import org.springframework.http.ResponseEntity;

public interface AuthorService {
    ResponseEntity<?> save(AuthorDto authorDto);
    ResponseEntity<?> update(AuthorDto authorDto, Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> getByName(String name);
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> getAll();
}
