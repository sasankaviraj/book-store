package com.sasanka.author_service.service.impl;


import com.sasanka.author_service.dto.AuthorDto;
import com.sasanka.author_service.dto.response.SuccessDto;
import com.sasanka.author_service.entity.Author;
import com.sasanka.author_service.entity.User;
import com.sasanka.author_service.exception.NotFoundException;
import com.sasanka.author_service.repository.AuthorRepository;
import com.sasanka.author_service.repository.UserRepository;
import com.sasanka.author_service.service.AuthorService;
import com.sasanka.author_service.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String SUCCESS_MSG = "Success" ;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<?> save(AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        String usernameFromJWT = jwtUtil.extractUsername(authorDto.getToken().substring(7));
        if(Objects.isNull(usernameFromJWT)) {
            throw new NotFoundException("Invalid user");
        }
        User user = userRepository.findByUsername(usernameFromJWT).orElseThrow(() -> new NotFoundException("Username not found"));
        author.setUser(user);
        authorRepository.save(author);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(AuthorDto authorDto, Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
        BeanUtils.copyProperties(authorDto, author);
        authorRepository.save(author);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
        authorRepository.delete(author);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getByName(String name) {
        Author author = authorRepository.findByFirstName(name).orElseThrow(() -> new NotFoundException("Author not found"));
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }
}
