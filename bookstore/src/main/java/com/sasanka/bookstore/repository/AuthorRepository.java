package com.sasanka.bookstore.repository;

import com.sasanka.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByFirstName(String name);
}
