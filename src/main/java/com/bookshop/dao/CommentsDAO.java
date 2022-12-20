package com.bookshop.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommentsDAO {
    public JSONArray getComments(int idproduct){
        JSONArray data = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select cus.name,cus.id as idcustomer,pro.id as idproduct, com.content as `comment` from Customers cus , Comments com , Products pro where cus.id = com.idcustomer and pro.id = com.productid and com.productid =?;");
            pstmt.setInt(1,idproduct);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject item = new JSONObject();
                item.put("name",rs.getString("name"));
                item.put("idCustomer",rs.getInt("idcustomer"));
                item.put("idProduct",rs.getInt("idproduct"));
                item.put("comment",rs.getString("comment"));
                item.put("star",rs.getInt("star"));
                data.put(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return data;
    }

    public int getStarNumber(int idproduct, int star){
        int numberStar = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select count(*) as sl from Comments where productid = ? and star = ?");
            pstmt.setInt(1,idproduct);
            pstmt.setInt(2,star);
            rs = pstmt.executeQuery();
            if (rs.next()){
                numberStar = rs.getInt("sl");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return numberStar;
    }
}
