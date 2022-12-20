package com.bookshop.repository;

import com.bookshop.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    @Query("select p from OrderDetail p where p.customerid = :customerid")
    List<OrderDetail> getOrderDetailByCustomerId (@Param("customerid") int customerid);
}
