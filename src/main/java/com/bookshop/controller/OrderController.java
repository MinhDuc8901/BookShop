package com.bookshop.controller;

import com.bookshop.dao.OrderDAO;
import com.bookshop.dao.OrderDetailDAO;
import com.bookshop.entities.Session;
import com.bookshop.service.SessionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private SessionService sesSer;

    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    // lấy ra tất cả danh sách các sản phầm mà khách hàng đã đặt
    @PostMapping("/all")
    public ResponseEntity<?> getListOrder(@RequestBody String data){
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
            response.put("results",orderDAO.getListOrder(session.getCustomerid()));
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }

    // hủy bỏ đơn mà khách hàng đã đặt
    @PostMapping("/remove")
    public ResponseEntity<?> removeOrder (@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        int idOrder = readData.getInt("orderId");
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
            response.put("results",orderDAO.updateRemoveOrder(idOrder));
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }

    // chọn những sản phẩm mà khách hàng và đặt
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        JSONArray arrayOrder = readData.getJSONArray("listOrderId");
        String sessionId = readData.getString("sessionId");
        Double totalprice = readData.getDouble("totalprice");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            int keyOrder = orderDAO.addOrder(session.getCustomerid(),totalprice);
            for(int i=0; i< arrayOrder.length();i++){
                int id = arrayOrder.getInt(i);
                orderDetailDAO.updateOrderDetail(id,keyOrder);
            }
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",orderDAO.getListOrder(session.getCustomerid()));
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
}
