package com.bookshop.service;

import com.bookshop.dao.CommentsDAO;
import com.bookshop.entities.Comments;
import com.bookshop.repository.CommentsRepository;
import com.bookshop.repository.ProductsRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private ProductsRepository proRepo;
    @Autowired
    private CommentsRepository commentsRepo;

    private CommentsDAO comDao = new CommentsDAO();

    public JSONArray getComments(int idProduct){
        return comDao.getComments(idProduct);
    }
    public void saveComment(Comments comments){
        commentsRepo.save(comments);
    }
}
