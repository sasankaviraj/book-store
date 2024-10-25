package com.sasanka.book_service.service.impl;

import com.sasanka.book_service.dto.BookDto;
import com.sasanka.book_service.dto.response.SuccessDto;
import com.sasanka.book_service.entity.Author;
import com.sasanka.book_service.entity.Book;
import com.sasanka.book_service.exception.NotFoundException;
import com.sasanka.book_service.repository.BookRepository;
import com.sasanka.book_service.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sasanka.book_service.Urls.AUTHOR_URL;


@Service
public class BookServiceImpl implements BookService {

    private static final String SUCCESS_MSG = "Success";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<?> save(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        List<Author> authorList = new ArrayList<>();
        bookDto.getAuthorIds().forEach(id -> {
            Author author = restTemplate.getForObject(AUTHOR_URL.concat(id.toString()), Author.class);
            if (Objects.isNull(author))
                throw new NotFoundException("Author Not Found");
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
            Author author = restTemplate.getForObject(AUTHOR_URL.concat(id.toString()), Author.class);
            if (Objects.isNull(author))
                throw new NotFoundException("Author Not Found");
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
