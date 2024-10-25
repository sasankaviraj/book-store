package com.sasanka.book_service.repository;

import com.sasanka.book_service.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :bookName, '%')) " +
            "OR b.isbn = :isbn")
    List<Book> searchBooks(@Param("bookName") String bookName, @Param("isbn") String isbn);
}
