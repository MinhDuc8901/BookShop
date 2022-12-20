package com.bookshop.service;

import com.bookshop.entities.Products;
import com.bookshop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productRepo;

    public List<Products> getListProducts(){
        return productRepo.findAll();
    }

    public Products getProduct(int id){
        return productRepo.findById(id).get();
    }

    public Products saveProduct(Products products){
        return productRepo.save(products);
    }

    public void deleteProduct(int id){
        Products products = getProduct(id);
        productRepo.delete(products);
    }

    public List<Products> searchProducts (String nameProduct){
        return productRepo.searchProductsByName(nameProduct);
    }

    public List<Products> getListProductHot(){
        return productRepo.getListProductHot();
    }
}
