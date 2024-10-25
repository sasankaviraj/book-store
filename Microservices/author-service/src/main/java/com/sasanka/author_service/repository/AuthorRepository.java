package com.sasanka.author_service.repository;

import com.sasanka.author_service.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByFirstName(String name);
}
