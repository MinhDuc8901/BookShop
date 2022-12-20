package com.bookshop.service;

import com.bookshop.entities.OrderDetail;
import com.bookshop.repository.OrderDetailRepository;
import com.bookshop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private OrderDetailRepository cartRepo;
    @Autowired
    private ProductsRepository proRepo;

    public List<OrderDetail> getListCartByCustomer (int Customerid){
        List<OrderDetail> cart = null;
        try{
            cart = cartRepo.getOrderDetailByCustomerId(Customerid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cart;
    }

    public OrderDetail saveCart (OrderDetail orderDetail){
        return cartRepo.save(orderDetail);
    }
    public void deleteCart (int id){
        OrderDetail order = cartRepo.findById(id).get();
        cartRepo.delete(order);
    }
}
