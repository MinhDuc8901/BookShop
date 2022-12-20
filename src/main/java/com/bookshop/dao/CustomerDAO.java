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
}
