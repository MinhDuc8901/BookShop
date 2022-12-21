package com.bookshop.controller;

import com.bookshop.dao.CustomerDAO;
import com.bookshop.dao.ProductsDAO;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ProductsDAO productDao = new ProductsDAO();

    // convert string to date
    private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

    // đăng nhập bằng quyền admin
    // nếu mà roleid = 1 thì đây là quyền cho khách hàng
    // nếu mà roleid = 2 thì đây là quyền admin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String data) {
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String email = readData.getString("email");
        String password = readData.getString("password");
        // kết thúc tham số nhận
        Customers getCustomer = cusService.getCustomer(email);
        JSONObject response = new JSONObject();
        if (getCustomer != null) {
            if (customerDao.getRole(getCustomer.getRoleid()).getInt("admin") == 1) {
                if (getCustomer.getPassword().equals(password)) {
                    UUID createSession = UUID.randomUUID();
                    Session session = cusService.SaveSession(getCustomer.getId(), createSession.toString());
                    response.put("session_id", createSession.toString());
                    response.put("results", customerDao.getCustomer(getCustomer.getId()));
                    response.put("code", 200);
                    response.put("description", "Đặng nhập thành công");
                    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
                } else {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new Response(400, "Vui lòng nhập lại mật khẩu.", ""));
                }
            } else {
                return ResponseEntity.ok(new Response(400, "Tài khoản đăng nhập không hợp lệ", ""));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response(400, "Tài khoản không tồn tại.", ""));
        }

    }

    // Thêm sản phẩm
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody String data) throws ParseException {
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        int categoryid = readData.getInt("categoryid");
        String name = readData.getString("name");
        String description = readData.getString("description");
//        String content = readData.getString("content");
        String photo = readData.getString("photo");
        int hot = readData.getInt("hot");
        Double price = readData.getDouble("price");
        int discount = readData.getInt("discount");
        String author = readData.getString("author");
        int pagenumber = readData.getInt("pagenumber");
        String create = readData.getString("create");
        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                Products product = new Products();
                product.setAuthor(author);
                product.setCategoryid(categoryid);
    //            product.setContent(content);
    //            product.setCreate(new Date());
                product.setDescription(description);
                product.setDiscount(discount);
                product.setName(name);
                product.setPagenumber(pagenumber);
                product.setPhoto(photo);
                product.setHot(hot);
                product.setPrice(price);
                 product.setCreate(sf.parse(create));
                productDao.insertProduct(product);
                response.put("code", 200);
                response.put("description", "Thành công");
                response.put("results", productDao.getListProduct());
                return ResponseEntity.status(HttpStatus.OK).body(response.toString());
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }

    // Lấy ra tất cả sản phẩm
    @GetMapping("/allProduct")
    public ResponseEntity<?> getListProducts() {
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("description", "Thành công");
//        response.put("results", proSer.getListProducts());
        response.put("results", productDao.getListProduct());
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    // sửa sản phẩm
    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody String data) throws ParseException {
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        int id = readData.getInt("id");
        int categoryid = readData.getInt("categoryid");
        String name = readData.getString("name");
        String description = readData.getString("description");
//        String content = readData.getString("content");
        String photo = readData.getString("photo");
        int hot = readData.getInt("hot");
        Double price = readData.getDouble("price");
        int discount = readData.getInt("discount");
        String author = readData.getString("author");
        String create = readData.getString("create");
        int pagenumber = readData.getInt("pagenumber");

        String sessionId = readData.getString("sessionId");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                Products product = new Products();
                product.setId(id);
                product.setAuthor(author);
                product.setCategoryid(categoryid);
//                product.setContent(content);
                product.setCreate(sf.parse(create));
                product.setDescription(description);
                product.setDiscount(discount);
                product.setName(name);
                product.setPagenumber(pagenumber);
                product.setPhoto(photo);
                product.setHot(hot);
                product.setPrice(price);
                productDao.updateProduct(product);
                response.put("code", 200);
                response.put("description", "Thành công");
                response.put("results", productDao.getListProduct());
                return ResponseEntity.status(HttpStatus.OK).body(response.toString());
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }

    // xóa sản phẩm
    @PostMapping("/remove")
    public ResponseEntity<?> removeProduct(@RequestBody String data) {
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        int idProduct = readData.getInt("idProduct");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                proSer.deleteProduct(idProduct);
                response.put("code", 200);
                response.put("description", "Thành công");
                response.put("results", productDao.getListProduct());
                return ResponseEntity.status(HttpStatus.OK).body(response.toString());
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }
    // Nguyễn Minh Đức 21/12/2022 // Bổ sung
    // Thêm category vào trong database
    @PostMapping("/category/add")
    public ResponseEntity<?> addCategory (@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // Nhận tham số truyền vào
        String sessionId = readData.getString("sessionId");
        String nameCategory = readData.getString("name");
        // Kết thúc nhận tham số.
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                boolean insertCategory = productDao.insertCategory(nameCategory);
                if (insertCategory){
                    response.put("code",200);
                    response.put("description","Thành công");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }else {
                    response.put("code",400);
                    response.put("description","Vui lòng thử lại");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }

    // Sửa tên Category
    @PostMapping("/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // Đọc tham số truyền vào
        String sessionId = readData.getString("sessionId");
        String nameCategory = readData.getString("name");
        int idCategory = readData.getInt("id");
        // Kết thúc tham số truyền vào.
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                boolean check = productDao.updateCategory(nameCategory,idCategory);
                if(check){
                    response.put("code",200);
                    response.put("description","Thành công");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }else{
                    response.put("code",400);
                    response.put("description","Sửa thông tin không thành công");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }
    // xóa Category
    @PostMapping("/category/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // Đọc tham số truyền vào
        String sessionId = readData.getString("sessionId");
        int idCategory = readData.getInt("id");
        // Kết thúc tham số truyền vào.
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if (session == null) {
            response.put("code", 400);
            response.put("description", "Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            if(customerDao.getCustomer(session.getCustomerid()).getInt("roleId")==2){
                boolean check = productDao.deleteCategory(idCategory);
                if(check){
                    response.put("code",200);
                    response.put("description","Thành công");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }else {
                    response.put("code",400);
                    response.put("description","Không thành công");
                    response.put("results",productDao.getCategory());
                    return ResponseEntity.ok(response.toString());
                }
            }else{
                response.put("code",400);
                response.put("description","Tài khoản của bạn không có quyền đăng nhập");
                response.put("results","");
                return ResponseEntity.ok(response.toString());
            }
        }
    }

}
