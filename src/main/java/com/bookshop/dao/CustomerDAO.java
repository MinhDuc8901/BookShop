package com.bookshop.dao;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {
    public JSONObject getCustomer(int idCustomer){
        JSONObject response = new JSONObject();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Customers where id = ?");
            pstmt.setInt(1,idCustomer);
            rs = pstmt.executeQuery();
            if(rs.next()){
                response.put("name",rs.getString("name"));
                response.put("email",rs.getString("email"));
                response.put("address",rs.getString("address"));
                response.put("photo",rs.getString("photo"));
                response.put("phone",rs.getString("phone"));
                response.put("roleId",rs.getInt("roleid"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }

    public JSONObject getRole(int idRole){
        JSONObject response = new JSONObject();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Role where id = ?");
            pstmt.setInt(1,idRole);
            rs = pstmt.executeQuery();
            if(rs.next()){
                response.put("id",rs.getInt("id"));
                response.put("admin",rs.getInt("admin"));
                response.put("buy",rs.getInt("buy"));
                response.put("crud",rs.getInt("crud"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
    // update thông tin cá nhân
    public boolean insertInfo(int id, String name, String email, String address,String phone, String passwd, String photo){
        boolean checkinsert = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("update Customers set name = ?, email = ?, address = ?, phone = ?, password = ?,photo=? where id = ?");
            pstmt.setString(1,name);
            pstmt.setString(2,email);
            pstmt.setString(3,address);
            pstmt.setString(4,phone);
            pstmt.setString(5,passwd);
            pstmt.setString(6,photo);
            pstmt.setInt(7,id);
            int a = pstmt.executeUpdate();
            if(a>0) checkinsert = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return checkinsert;
    }

}
