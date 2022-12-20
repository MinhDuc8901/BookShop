package com.bookshop.controller;

import com.bookshop.dao.CommentsDAO;
import com.bookshop.dao.ProductsDAO;
import com.bookshop.entities.Comments;
import com.bookshop.entities.Products;
import com.bookshop.entities.Session;
import com.bookshop.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductsController {
    @Autowired
    private ProductService proService;
    @Autowired
    private RatingService rateSer;
    @Autowired
    private CategoryService cateser;
    @Autowired
    private CommentService comSer;
    @Autowired
    private SessionService sesSer;

    private CommentsDAO comDAO = new CommentsDAO();

    private ProductsDAO productsDao = new ProductsDAO();

//    hàm lấy ra tất cả sản phẩm
    @GetMapping("/allproduct")
    public ResponseEntity<?> getListProduct(){
        List<Products> products = proService.getListProducts();
        JSONObject product = null;
        JSONArray data = new JSONArray();
        for(Products item : products){
            product = new JSONObject(item);
            JSONArray rate = rateSer.getProductStar(item.getId());
            product.put("rate",rate);
            data.put(product);
        }
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("result",data);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
//    Hàm lấy ra một sản phẩm
//    VD đường dẫn http://localhost:8080/api/v1/product/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") int id){
        Products product = proService.getProduct(id);
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",new JSONObject(product));
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
//    Hàm lấy những sản phẩm đang hot
    @GetMapping("/hot")
    public ResponseEntity<?> getProductHot(){
        List<Products> products = proService.getListProductHot();
        JSONObject product = null;
        JSONArray data = new JSONArray();
        for(Products item : products){
            product = new JSONObject(item);
            JSONArray rate = rateSer.getProductStar(item.getId());
            product.put("rate",rate);
            data.put(product);
        }
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",data);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

//    Hàm tìm kiếm sản phẩm theo tên
    @PostMapping("/search")
    public ResponseEntity<?> searchProducts (@RequestBody String data){
        JSONObject request = new JSONObject(data);
        // tham số nhận
        String nameProduct = request.getString("name");
        // kết thúc tham số nhận
        List<Products> products = proService.searchProducts(nameProduct);
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",products);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
    // lấy thông tin đánh giá của một sản phẩm số lượng sao
    // khuyến cáo không nên sử dụng api này nữa vì đã có api thay thế là api lấy danh sách comments của sản phẩm
    @GetMapping("/star/{id}")
    public ResponseEntity<?> getStarProduct(@PathVariable("id") int id){
        JSONArray data = rateSer.getProductStar(id);
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",data);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    // lấy danh sách sản phẩm theo loại sản phẩm
    @GetMapping("/category/{id}")// id là giá trị trong list danh sách category
    public ResponseEntity<?> getProductsCategory(@PathVariable("id") int id){
        JSONArray products = productsDao.getListCategory(id);
//        JSONObject product = null;
//        JSONArray data = new JSONArray();
//        for(Products item : products){
//            product = new JSONObject(item);
//            JSONArray rate = rateSer.getProductStar(item.getId());
////            product.put("rate",rate);
//            data.put(product);
//        }
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",products);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
    // Lấy danh sách comments của sản phẩm đó
    // VD http://localhost:8080/api/v1/product/comments/1
    // Chú ý phương thưc GET
    @GetMapping("/comments/{id}")// id ở đây tương ứng với id trong list danh sách sản phẩm
    public ResponseEntity<?> getCommets(@PathVariable("id") int id){
        JSONObject response = new JSONObject();
        JSONArray data = productsDao.getComment(id);
        JSONObject starAll = new JSONObject();
        for (int i = 1; i<=5 ; i++){
            starAll.put("star_"+i,comDAO.getStarNumber(id,i));
        }
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",data);
        response.put("dataStar",starAll);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
    // Thêm comment vào một sản phẩm nào đó
    @PostMapping("/comment")
    public ResponseEntity<?> insertComments(@RequestBody String data){
        JSONObject readData = new JSONObject(data);
        // tham số nhận
        String sessionId = readData.getString("sessionId");
        int productId = readData.getInt("productId");
        String comment = readData.getString("comment");
        int star = readData.getInt("star");
        // kết thúc tham số nhận
        Session session = sesSer.getSession(sessionId);
        JSONObject response = new JSONObject();
        if(session == null){
            response.put("code",400);
            response.put("description","Vui lòng đăng nhập lại");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }else{
            Comments com = new Comments();
            com.setContent(comment);
            com.setIdcustomer(session.getCustomerid());
            com.setProductid(productId);
            com.setStar(star);
            comSer.saveComment(com);
            JSONArray dataCom = productsDao.getComment(productId);
            JSONObject starAll = new JSONObject();
            for (int i = 1; i<=5 ; i++){
                starAll.put("star_"+i,comDAO.getStarNumber(productId,i));
            }
            response.put("code",200);
            response.put("description", "Thành công");
            response.put("results",dataCom);
            response.put("dataStar",starAll);
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
    // lấy list danh sách category
    @GetMapping("/category")
    public ResponseEntity<?> getListCategory(){
        JSONObject response = new JSONObject();
        JSONArray data = productsDao.getCategory();
        response.put("code",200);
        response.put("description","Thành công");
        response.put("results",data);
        return ResponseEntity.ok(response.toString());
    }
}
