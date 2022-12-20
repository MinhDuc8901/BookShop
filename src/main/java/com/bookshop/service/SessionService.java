package com.bookshop.service;

import com.bookshop.entities.Session;
import com.bookshop.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sesRepo;
    public Session getSession(String sessionId){
        Session session = null;
        try{
            session = sesRepo.findBySession(sessionId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return session;
    }
}
