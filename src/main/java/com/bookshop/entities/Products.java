package com.bookshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    private int id;
    private int categoryid;
    private String name;
    private String description;
    private String content;
    private String photo;
    private int hot;
    private Double price;
    private int discount;
    private int pagenumber;
    private Date create;
    private String author;
}
