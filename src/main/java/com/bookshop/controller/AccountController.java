package com.bookshop.controller;

import com.bookshop.dao.CustomerDAO;
import com.bookshop.entities.Customers;
import com.bookshop.entities.Role;
import com.bookshop.entities.Session;
import com.bookshop.model.Response;
import com.bookshop.model.ResponseObject;
import com.bookshop.service.CustomersService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// code
// 200 Thành công
// 400 Không thành công
//
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private CustomersService cusService;

    private CustomerDAO customerDao = new CustomerDAO();

//  hàm login vào chuyền email và password trả về sẽ là một session và thông tin khách hàng.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String email = readData.getString("email");
        String password = readData.getString("password");
        // kết thúc tham số nhận
        Customers getCustomer = cusService.getCustomer(email);
        JSONObject response = new JSONObject();
        if(getCustomer!=null){
            if(getCustomer.getPassword().equals(password)){
                UUID createSession = UUID.randomUUID();
                Session session = cusService.SaveSession(getCustomer.getId(), createSession.toString());
                response.put("session_id",createSession.toString());
                response.put("results",customerDao.getCustomer(getCustomer.getId()));
                response.put("code",200);
                response.put("description","Đăng nhập thành công");
                System.out.println(response.toString());
                return ResponseEntity.status(HttpStatus.OK).body(response.toString());
            }
            else{
                return ResponseEntity.status(HttpStatus.OK).body(new Response(400,"Vui lòng nhập lại mật khẩu.",""));
            }
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Response(400,"Tài khoản không tồn tại vui lòng đăng ký tài khoản.",""));
        }
    }

//    Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String data){
        JSONObject getData = new JSONObject(data);
        // tham số nhận
        String name = getData.getString("name");
        String email = getData.getString("email");
        String address = getData.getString("address");
        String phone = getData.getString("phone");
        String password = getData.getString("password");
        // kết thúc tham số nhận
//        int [] arrayRole = {0,1,0};
//        Role role = cusService.createRole(arrayRole);
//        System.out.println(role.toString());
        Customers customers = new Customers();
        customers.setName(name);
        customers.setEmail(email);
        customers.setAddress(address);
        customers.setPhone(phone);
        customers.setPassword(password);
        customers.setRoleid(1);
        Customers checkCustomer = null;
        checkCustomer = cusService.getCustomer(customers.getEmail());
        if(checkCustomer != null){
            return ResponseEntity.status(HttpStatus.OK).body(new Response(400,"Tài khoản đã tồn tại",""));
        }
        customers = cusService.saveCustomer(customers);
        JSONObject response = new JSONObject(customers);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(200,"Đăng ký thành công.",response.toString()));
    }
}
