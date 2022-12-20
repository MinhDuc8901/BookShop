package com.bookshop.repository;

import com.bookshop.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    @Query("select p from Orders p where p.customerid = :cusId")
    List<Orders> getListOrderCustomer(@Param("cusId")int cusid);

    @Query("select p from Orders p where p.customerid = :cusid and p.create = :create and p.price = :price")
    Orders getOrder(@Param("cusid") int cusid, @Param("create")Date create,@Param("price")Double price);


}
