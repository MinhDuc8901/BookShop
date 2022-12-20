package com.bookshop.repository;

import com.bookshop.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    @Query("select p from Products p where p.name like %:name%")
    List<Products> searchProductsByName(@Param("name") String name);

    @Query("select p from Products  p where p.hot = 1")
    List<Products> getListProductHot();

    @Query("select p from Products  p where p.categoryid = : idcate")
    List<Products> getListProductCate(@Param("idcate") int idcate);
}
