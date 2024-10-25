package com.sasanka.bookstore.service;

import com.sasanka.bookstore.dto.BookDto;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<?> save(BookDto bookDto);
    ResponseEntity<?> update(BookDto bookDto, Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> searchByNameOrIsbn(String searchTerm);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getAllPageable(int page, int size);
}
