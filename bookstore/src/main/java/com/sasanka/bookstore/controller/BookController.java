package com.sasanka.bookstore.controller;

import com.sasanka.bookstore.dto.BookDto;
import com.sasanka.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sasanka.bookstore.util.Constants.BASE_URL_BOOK;

@RestController
@RequestMapping(BASE_URL_BOOK)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody BookDto bookDto){
        return bookService.save(bookDto);
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody BookDto bookDto, @PathVariable("id") Long id){
        return bookService.update(bookDto, id);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete( @PathVariable("id") Long id){
        return bookService.delete(id);
    }

    @GetMapping(value = "/search/{term}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search( @PathVariable("term") String term){
        return bookService.searchByNameOrIsbn(term);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        return bookService.getAll();
    }

    @GetMapping(value = "/getPageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(@RequestParam("page") int page, @RequestParam("size") int size){
        return bookService.getAllPageable(page,size);
    }
}
