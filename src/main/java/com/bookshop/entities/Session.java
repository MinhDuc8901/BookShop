package com.bookshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    private int id;
    private int customerid;
    private String sessionid;
}
