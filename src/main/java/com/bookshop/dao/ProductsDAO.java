package com.bookshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import com.bookshop.entities.Products;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProductsDAO {
    private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

    public boolean insertProduct(Products product) {
        boolean checkinsert = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement(
                    "insert into products (categoryid,name,description,content,photo,hot,price,discount,pagenumber,author,`create`) value(?,?,?,?,?,?,?,?,?,?,?);");
            pstmt.setInt(1, product.getCategoryid());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, "");//product.getContent()
            pstmt.setString(5, product.getPhoto());
            pstmt.setInt(6, product.getHot());//
            pstmt.setDouble(7, product.getPrice());
            pstmt.setInt(8, product.getDiscount());
            pstmt.setInt(9, product.getPagenumber());
            pstmt.setString(10, product.getAuthor());
            pstmt.setDate(11, new java.sql.Date(product.getCreate().getTime()));
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
    public boolean updateProduct(Products product){
        boolean checkupdate = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("UPDATE products\n" +
                    "SET categoryid = ?, name = ?, description = ? , content = ? , photo = ? , hot = ?, price = ? ,discount = ?,`create` = ?, pagenumber = ?, author =? \n" +
                    "WHERE id = ?; ");
            pstmt.setInt(1,product.getCategoryid());
            pstmt.setString(2,product.getName());
            pstmt.setString(3,product.getDescription());
            pstmt.setString(4,"");//product.getContent()
            pstmt.setString(5,product.getPhoto());
            pstmt.setInt(6,product.getHot());//
            pstmt.setDouble(7,product.getPrice());
            pstmt.setInt(8,product.getDiscount());
            pstmt.setDate(9,new Date(product.getCreate().getTime()));
            pstmt.setInt(10,product.getPagenumber());
            pstmt.setString(11,product.getAuthor());
            pstmt.setInt(12,product.getId());
            int check = pstmt.executeUpdate();
            if(check>0) checkupdate = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return checkupdate;
    }
    public JSONArray getListCategory(int idCategory){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Products where categoryid = ?");
            pstmt.setInt(1,idCategory);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("id"));
                obj.put("categoryid",rs.getInt("categoryid"));
                obj.put("name",rs.getString("name"));
                obj.put("description",rs.getString("description"));
//                obj.put("content",rs.getString("content"));
                obj.put("photo",rs.getString("photo"));
                obj.put("hot",rs.getInt("hot"));
                obj.put("price",rs.getDouble("price"));
                obj.put("discount",rs.getInt("discount"));
                obj.put("create",rs.getDate("create"));
                obj.put("pagenumber",rs.getInt("pagenumber"));
                obj.put("author",rs.getString("author"));
                response.put(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
    public JSONArray getComment(int idProduct){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Comments where productid = ?");
            pstmt.setInt(1,idProduct);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject item = new JSONObject();
                item.put("id",rs.getInt("id"));
                item.put("productid",rs.getInt("productid"));
                item.put("content",rs.getString("content"));
                item.put("idcustomer",rs.getInt("idcustomer"));
                item.put("star",rs.getInt("star"));
                response.put(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
    public JSONArray getCategory(){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from Categories ;");
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("id"));
                obj.put("name",rs.getString("name"));
                response.put(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
//  Nguyễn Minh Đức 21/12/2022 Start.
    public JSONArray getListProduct(){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("SELECT * from Products");
            rs = pstmt.executeQuery();
            while(rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("id"));
                obj.put("categoryid",rs.getInt("categoryid"));
                obj.put("name",rs.getString("name"));
                obj.put("description",rs.getString("description"));
//                obj.put("content",rs.getString("content"));
                obj.put("photo",rs.getString("photo"));
                obj.put("hot",rs.getInt("hot"));
                obj.put("price",rs.getDouble("price"));
                obj.put("discount",rs.getInt("discount"));
                java.util.Date date = new Date(rs.getDate("create").getTime());
                obj.put("create",sf.format(date));
                obj.put("pagenumber",rs.getInt("pagenumber"));
                obj.put("author",rs.getString("author"));
                response.put(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
    public boolean insertCategory(String name){
        boolean checkinsert = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("INSERT INTO Categories (`parentid`, `name`) VALUES ('1', ?);");
            pstmt.setString(1,name);
            int check = pstmt.executeUpdate();
            if (check>0) checkinsert = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return  checkinsert;
    }

    public boolean updateCategory(String name , int id){
        boolean checkupdate = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("UPDATE categories\n" +
                    "SET `name` = ?\n" +
                    "WHERE id = ?;");
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
            int update = pstmt.executeUpdate();
            if (update>0) checkupdate = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return checkupdate;
    }

    public boolean deleteCategory(int id){
        boolean checkdelete = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("DELETE FROM categories WHERE id = ?;");
            pstmt.setInt(1,id);
            int check = pstmt.executeUpdate();
            if (check>0) checkdelete = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return checkdelete;
    }
    
    public JSONObject getProductId(int id){
        JSONObject response = new JSONObject();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from products where id = ?;");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                response.put("id",rs.getInt("id"));
                response.put("categoryid",rs.getInt("categoryid"));
                response.put("name",rs.getString("name"));
                response.put("description",rs.getString("description"));
//                obj.put("content",rs.getString("content"));
                response.put("photo",rs.getString("photo"));
                response.put("hot",rs.getInt("hot"));
                response.put("price",rs.getDouble("price"));
                response.put("discount",rs.getInt("discount"));
                java.util.Date date = new Date(rs.getDate("create").getTime());
                response.put("create",sf.format(date));
                response.put("pagenumber",rs.getInt("pagenumber"));
                response.put("author",rs.getString("author"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }
    public JSONArray getListProductHot(){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from products where hot =1");
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("id"));
                obj.put("categoryid",rs.getInt("categoryid"));
                obj.put("name",rs.getString("name"));
                obj.put("description",rs.getString("description"));
//                obj.put("content",rs.getString("content"));
                obj.put("photo",rs.getString("photo"));
                obj.put("hot",rs.getInt("hot"));
                obj.put("price",rs.getDouble("price"));
                obj.put("discount",rs.getInt("discount"));
                java.util.Date date = new Date(rs.getDate("create").getTime());
                obj.put("create",sf.format(date));
                obj.put("pagenumber",rs.getInt("pagenumber"));
                obj.put("author",rs.getString("author"));
                response.put(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }

    public JSONArray getListProductName(String name){
        JSONArray response = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from products where name like ?");
            pstmt.setString(1,"%"+name+"%");
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("id"));
                obj.put("categoryid",rs.getInt("categoryid"));
                obj.put("name",rs.getString("name"));
                obj.put("description",rs.getString("description"));
//                obj.put("content",rs.getString("content"));
                obj.put("photo",rs.getString("photo"));
                obj.put("hot",rs.getInt("hot"));
                obj.put("price",rs.getDouble("price"));
                obj.put("discount",rs.getInt("discount"));
                java.util.Date date = new Date(rs.getDate("create").getTime());
                obj.put("create",sf.format(date));
                obj.put("pagenumber",rs.getInt("pagenumber"));
                obj.put("author",rs.getString("author"));
                response.put(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return response;
    }

}
