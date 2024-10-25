package com.sasanka.author_service.entity;

import com.sasanka.author_service.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookName;
    @Column(unique = true)
    private String isbn;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Author> authorList = new ArrayList<>();
    private LocalDate publishedDate;
    private Double price;
}
