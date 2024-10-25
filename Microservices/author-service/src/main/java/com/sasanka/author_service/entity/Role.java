package com.sasanka.author_service.entity;

import com.sasanka.author_service.enums.RoleName;
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
