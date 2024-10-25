package com.sasanka.bookstore.repository;

import com.sasanka.bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
