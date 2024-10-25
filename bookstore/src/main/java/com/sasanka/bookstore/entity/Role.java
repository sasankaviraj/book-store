package com.sasanka.bookstore.entity;

import com.sasanka.bookstore.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName roleName;
}
