package com.bookshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bookshop.entities.Products;

public class ProductsDAO {
    public boolean insertProduct(Products product) {
        boolean checkinsert = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement(
                    "insert into products (categoryid,name,description,content,photo,hot,price,discount,pagenumber,author,create) value(?,?,?,?,?,?,?,?,?,?,?);");
            pstmt.setInt(1, product.getCategoryid());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getContent());
            pstmt.setString(5, product.getPhoto());
            pstmt.setInt(6, product.getHot());
            pstmt.setDouble(7, product.getPrice());
            pstmt.setInt(8, product.getDiscount());
            pstmt.setInt(9, product.getPagenumber());
            pstmt.setString(10, product.getAuthor());
            pstmt.setDate(11, (Date) product.getCreate());
            int a = pstmt.executeUpdate();
            if (a > 0)
                checkinsert = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUntil.closeAll(con, pstmt, rs);
        }
        return checkinsert;
    }
}
