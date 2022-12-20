package com.bookshop.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderDAO {
    private OrderDetailDAO orderDetailDao = new OrderDetailDAO();
    public JSONArray getListOrder(int idCustomer){
        JSONArray array = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Orders where customerid = ?;");
            pstmt.setInt(1,idCustomer);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject item = new JSONObject();
                item.put("id",rs.getInt("id"));
                item.put("customerid",rs.getInt("customerid"));
                item.put("create",rs.getDate("create"));
                item.put("price",rs.getDouble("price"));
                item.put("status",rs.getInt("status"));
                item.put("product",orderDetailDao.getListProduct(rs.getInt("id")));
                array.put(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return array;
    }
    public boolean updateRemoveOrder(int idOrder){
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("UPDATE Orders\n" +
                    "SET `status` = 0\n" +
                    "WHERE id = ?;");
            pstmt.setInt(1,idOrder);
            int a = pstmt.executeUpdate();
            if(a>0){
                check = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return check;
    }
    public int addOrder(int customerid ,Double price){
        int check = -1;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("INSERT INTO Orders(customerid,price) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,customerid);
            pstmt.setDouble(2,price);
            int a = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                check = id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return check;
    }

}
