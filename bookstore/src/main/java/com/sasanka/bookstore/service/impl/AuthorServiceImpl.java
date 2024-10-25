package com.sasanka.bookstore.service.impl;

import com.sasanka.bookstore.auth.JwtUtil;
import com.sasanka.bookstore.dto.AuthorDto;
import com.sasanka.bookstore.dto.response.SuccessDto;
import com.sasanka.bookstore.entity.Author;
import com.sasanka.bookstore.entity.User;
import com.sasanka.bookstore.exception.NotFoundException;
import com.sasanka.bookstore.repository.AuthorRepository;
import com.sasanka.bookstore.repository.UserRepository;
import com.sasanka.bookstore.service.AuthorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.sasanka.bookstore.util.Constants.SUCCESS_MSG;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> save(AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        String usernameFromJWT = jwtUtil.extractUsername(authorDto.getToken().substring(7));
        if(Objects.isNull(usernameFromJWT)) {
            throw new NotFoundException("Invalid user");
        }
        User user = userRepository.findByUsername(usernameFromJWT).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
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
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }
}
