package com.bookshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "OrderDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    private int id;
    private int orderid;
    private int customerid;
    private int quantity;
    private Double price;
    private Date create;
}
