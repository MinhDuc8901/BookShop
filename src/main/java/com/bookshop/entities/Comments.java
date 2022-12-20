package com.bookshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    private int id;
    private int productid;
    private int idcustomer;
    private String content;
    private int star;
}
