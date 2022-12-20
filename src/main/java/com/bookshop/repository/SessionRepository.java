package com.bookshop.repository;

import com.bookshop.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query("select p from Session p where p.sessionid = :session")
    Session findBySession(@Param("session") String session);
}
