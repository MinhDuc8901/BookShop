package com.bookshop.dao;

import com.bookshop.repository.ProductsRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDetailDAO {
    @Autowired
    private ProductsRepository proRepo;

    public boolean saveOrderDetail(int idCustomer , int idProduct, int quantity , double price){
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("insert into OrderDetail(customerid,productid,quantity,price) values (?,?,?,?)");
            pstmt.setInt(1,idCustomer);
            pstmt.setInt(2,idProduct);
            pstmt.setInt(3,quantity);
            pstmt.setDouble(4,price);
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
    public JSONArray listCartCustomer(int idCustomer){
        JSONArray data = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select od.id as idorderdetail, od.quantity, od.price,od.create, pro.id as productid, pro.photo, pro.name, pro.discount from OrderDetail od , Products pro where od.productid = pro.id and od.customerid = ? and od.status=0;");
            pstmt.setInt(1,idCustomer);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject item = new JSONObject();
                item.put("idorderdetail",rs.getInt("idorderdetail"));
                item.put("quantity",rs.getInt("quantity"));
                item.put("price",rs.getDouble("price"));
                item.put("create",rs.getDate("create"));
                item.put("productid",rs.getInt("productid"));
                item.put("photo",rs.getString("photo"));
                item.put("name",rs.getString("name"));
                item.put("discount",rs.getInt("discount"));
                data.put(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return data;
    }
    public boolean removeCart(int idCustomer, int productid){
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("DELETE FROM OrderDetail WHERE customerid = ? and id = ?;");
            pstmt.setInt(1,idCustomer);
            pstmt.setInt(2,productid);
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

    public JSONArray getListProduct(int idorder){
        JSONArray array = new JSONArray();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select od.id as idorderdetail, od.quantity,od.price , od.create , p.name, p.photo, p.content,p.discount from OrderDetail od , Products p where od.productid = p.id and od.orderid = ? ;");
            pstmt.setInt(1, idorder);
            rs = pstmt.executeQuery();
            while (rs.next()){
                JSONObject item = new JSONObject();
                item.put("idorderdetail",rs.getInt("idorderdetail"));
                item.put("quantity",rs.getInt("quantity"));
                item.put("price",rs.getDouble("price"));
                item.put("create",rs.getTimestamp("create"));

                item.put("name",rs.getString("name"));
                item.put("photo",rs.getString("photo"));
                item.put("discount",rs.getDouble("discount"));
                item.put("content",rs.getString("content"));
                array.put(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return array;
    }

    public boolean updateOrderDetail(int id,int idorder){
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("update OrderDetail set orderid = ? , status = 1 where id = ?;");
            pstmt.setInt(1,idorder);
            pstmt.setInt(2,id);
            int a = pstmt.executeUpdate();
            if(a>0) check = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return check;
    }

    public boolean checkAddCart(int idCustomer , int idProduct){
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = DBUntil.openConnection();
            pstmt = con.prepareStatement("select * from OrderDetail where customerid = ? and productid = ? and status = 0");
            pstmt.setInt(1,idCustomer);
            pstmt.setInt(2,idProduct);
            rs = pstmt.executeQuery();
            while (rs.next()){
                check = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUntil.closeAll(con,pstmt,rs);
        }
        return check;
    }
}
