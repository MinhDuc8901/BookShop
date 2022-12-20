package com.bookshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
    @Id
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String password;
    private int roleid;
}
