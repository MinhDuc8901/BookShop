package com.bookshop.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RatingDAO {
    public JSONArray getProductStar(int productid){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray data = new JSONArray();
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("SELECT star, COUNT(star) AS `quantity`\n" +
                    "  FROM rating where productid = ? \n" +
                    "  GROUP BY star;");
            pstmt.setInt(1,productid);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject report = new JSONObject();
                report.put("star",rs.getInt("star"));
                report.put("quantity",rs.getInt("quantity"));
                data.put(report);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return data;
    }

}
