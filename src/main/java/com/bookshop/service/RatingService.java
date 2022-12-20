package com.bookshop.service;

import com.bookshop.dao.RatingDAO;
import com.bookshop.entities.rating;
import com.bookshop.repository.ratingRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private ratingRepository ratingRep;
    private RatingDAO rateDao = new RatingDAO();

    public void insertRating (int idProduct , int star){
        rating rate = new rating();
        rate.setProductid(idProduct);
        rate.setStart(star);
        ratingRep.save(rate);
    }

    public JSONArray getProductStar (int idProduct){
        JSONArray productsStar = rateDao.getProductStar(idProduct);
        return productsStar;
    }

}
