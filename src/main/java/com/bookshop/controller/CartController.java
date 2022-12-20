package com.bookshop.controller;

import com.bookshop.dao.OrderDetailDAO;
import com.bookshop.entities.Session;
import com.bookshop.service.SessionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private OrderDetailDAO orderDetaildao = new OrderDetailDAO();
    @Autowired
    private SessionService sesSer;


    // thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        int productId = readData.getInt("productId");
        int quantity = readData.getInt("quantity");
        Double price = readData.getDouble("price");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            boolean checksave = orderDetaildao.saveOrderDetail(session.getCustomerid(),productId,quantity,price);
            if(checksave){
                response.put("code",200);
                response.put("description","Thành công");
                response.put("results","");
                return ResponseEntity.status(HttpStatus.OK).body(response.toString());
            }
            response.put("code",400);
            response.put("description","Lỗi cơ sở dữ liệu");
            response.put("description","");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
    // lấy ra tắt cả sản phẩm trong giỏ hàng của khách hàng
    @PostMapping("/all")
    public ResponseEntity<?> getListCart(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",orderDetaildao.listCartCustomer(session.getCustomerid()));
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
    // xóa sản phẩm đó ra khỏi giỏ hàng của khách hàng
    @PostMapping("/remove")
    public ResponseEntity<?> removeItemCard(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        int idorderdetail = readData.getInt("idorderdetail");
        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            boolean check = orderDetaildao.removeCart(session.getCustomerid(),idorderdetail);
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",orderDetaildao.listCartCustomer(session.getCustomerid()));
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }

}
