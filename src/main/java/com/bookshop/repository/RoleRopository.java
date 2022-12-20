package com.bookshop.repository;

import com.bookshop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRopository extends JpaRepository<Role, Integer> {
}
