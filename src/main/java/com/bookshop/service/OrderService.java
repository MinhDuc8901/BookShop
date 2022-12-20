package com.bookshop.service;

import com.bookshop.entities.Orders;
import com.bookshop.repository.CustomersRepository;
import com.bookshop.repository.OrderDetailRepository;
import com.bookshop.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDetailRepository cartRepo;
    @Autowired
    private OrdersRepository ordersRepo;
    @Autowired
    private CustomersRepository cusRepo;

    public List<Orders> getListOrders(int cusId){
        return ordersRepo.getListOrderCustomer(cusId);
    }

    public Orders getOrderNow(int cusId, Date create , Double price){
        return ordersRepo.getOrder(cusId,create,price);
    }



}
