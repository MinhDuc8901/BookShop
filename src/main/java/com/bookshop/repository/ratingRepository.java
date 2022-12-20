package com.bookshop.repository;

import com.bookshop.entities.rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ratingRepository extends JpaRepository<rating , Integer> {
}
