package com.bookshop.controller;

import com.bookshop.dao.CustomerDAO;
import com.bookshop.entities.Customers;
import com.bookshop.entities.Products;
import com.bookshop.entities.Session;
import com.bookshop.model.Response;
import com.bookshop.service.CustomersService;
import com.bookshop.service.ProductService;
import com.bookshop.service.SessionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private CustomersService cusService;
    @Autowired
    private SessionService sesSer;
    @Autowired
    private ProductService proSer;

    private CustomerDAO customerDao = new CustomerDAO();

//    đăng nhập bằng quyền admin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String email = readData.getString("email");
        String password = readData.getString("password");
        // kết thúc tham số nhận
        Customers getCustomer = cusService.getCustomer(email);
        if(customerDao.getRole(getCustomer.getRoleid()).getInt("admin") == 1){
            JSONObject response = new JSONObject();
            if(getCustomer!=null){
                if(getCustomer.getPassword().equals(password)){
                    UUID createSession = UUID.randomUUID();
                    Session session = cusService.SaveSession(getCustomer.getId(), createSession.toString());
                    response.put("session_id",createSession.toString());
                    response.put("results",customerDao.getCustomer(getCustomer.getId()));
                    response.put("code",200);
                    response.put("description","Đặng nhập thành công");
                    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
                }
                else{
                    return ResponseEntity.status(HttpStatus.OK).body(new Response(400,"Vui lòng nhập lại mật khẩu.",""));
                }
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new Response(400,"Tài khoản không tồn tại vui lòng đăng ký tài khoản.",""));
            }
        }else{
            return ResponseEntity.ok(new Response(400,"Tài khoản đăng nhập không hợp lệ",""));
        }
    }
//    Thêm sản phẩm
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        int categoryid = readData.getInt("categoryid");
        String name = readData.getString("name");
        String description = readData.getString("description");
        String content = readData.getString("content");
        String photo = readData.getString("photo");
        int hot = readData.getInt("hot");
        Double price = readData.getDouble("price");
        int discount = readData.getInt("discount");
        String author = readData.getString("author");
        int pagenumber = readData.getInt("pagenumber");
        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            Products product = new Products();
            product.setAuthor(author);
            product.setCategoryid(categoryid);
            product.setContent(content);
            product.setCreate(new Date());
            product.setDescription(description);
            product.setDiscount(discount);
            product.setName(name);
            product.setPagenumber(pagenumber);
            product.setPhoto(photo);
            product.setHot(hot);
            product.setPrice(price);
            proSer.saveProduct(product);
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",proSer.getListProducts());
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
    // Lấy ra tất cả sản phẩm
    @GetMapping("/allProduct")
    public ResponseEntity<?> getListProducts (){
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",proSer.getListProducts());
        return  ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    // sửa sản phẩm
    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        int id = readData.getInt("id");
        int categoryid = readData.getInt("categoryid");
        String name = readData.getString("name");
        String description = readData.getString("description");
        String content = readData.getString("content");
        String photo = readData.getString("photo");
        int hot = readData.getInt("hot");
        Double price = readData.getDouble("price");
        int discount = readData.getInt("discount");
        String author = readData.getString("author");
        int pagenumber = readData.getInt("pagenumber");

        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            Products product = new Products();
            product.setId(id);
            product.setAuthor(author);
            product.setCategoryid(categoryid);
            product.setContent(content);
            product.setCreate(new Date());
            product.setDescription(description);
            product.setDiscount(discount);
            product.setName(name);
            product.setPagenumber(pagenumber);
            product.setPhoto(photo);
            product.setHot(hot);
            product.setPrice(price);
            proSer.saveProduct(product);
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",proSer.getListProducts());
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
    // xóa sản phẩm
    @PostMapping("/remove")
    public ResponseEntity<?> removeProduct(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        int idProduct = readData.getInt("idProduct");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            proSer.deleteProduct(idProduct);
            response.put("code",200);
            response.put("description","Thành công");
            response.put("results",proSer.getListProducts());
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }


}
