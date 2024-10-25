package com.sasanka.bookstore.service.impl;

import com.sasanka.bookstore.dto.BookDto;
import com.sasanka.bookstore.dto.response.SuccessDto;
import com.sasanka.bookstore.entity.Author;
import com.sasanka.bookstore.entity.Book;
import com.sasanka.bookstore.exception.NotFoundException;
import com.sasanka.bookstore.repository.AuthorRepository;
import com.sasanka.bookstore.repository.BookRepository;
import com.sasanka.bookstore.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.sasanka.bookstore.util.Constants.SUCCESS_MSG;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public ResponseEntity<?> save(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        List<Author> authorList = new ArrayList<>();
        bookDto.getAuthorIds().forEach(id -> {
            Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author Not Found"));
            authorList.add(author);
        });
        book.setAuthorList(authorList);
        bookRepository.save(book);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(BookDto bookDto, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Not Found"));
        List<Author> authorList = book.getAuthorList();
        BeanUtils.copyProperties(bookDto, book);
        bookDto.getAuthorIds().forEach(authorId -> {
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("Author Not Found"));
            if(!authorList.contains(author))
                authorList.add(author);
        });
        book.setAuthorList(authorList);
        bookRepository.save(book);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Not Found"));
        bookRepository.delete(book);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchByNameOrIsbn(String searchTerm) {
        List<Book> books = bookRepository.searchBooks(searchTerm, searchTerm);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllPageable(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(bookRepository.findAll(pageable), HttpStatus.OK);
    }

}
