package com.bookshop.repository;

import com.bookshop.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
    @Query("select p from Customers p where p.email = :email")
    Customers findByEmail(@Param("email")String email);
}
