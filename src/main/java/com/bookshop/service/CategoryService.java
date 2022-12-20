package com.bookshop.service;

import com.bookshop.entities.Products;
import com.bookshop.repository.CategoriesRepository;
import com.bookshop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoriesRepository cateRepo;
    @Autowired
    private ProductsRepository proRepo;

    public List<Products> getListProductCate(int idCategory){
        List<Products> products = null;
        try {
            products = proRepo.getListProductCate(idCategory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }
}
