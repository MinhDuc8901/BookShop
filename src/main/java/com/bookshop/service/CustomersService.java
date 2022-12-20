package com.bookshop.service;

import com.bookshop.entities.Customers;
import com.bookshop.entities.Role;
import com.bookshop.entities.Session;
import com.bookshop.repository.CustomersRepository;
import com.bookshop.repository.RoleRopository;
import com.bookshop.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {
    @Autowired
    private CustomersRepository customerRepo;
    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private RoleRopository roleRepo;


    public boolean CheckSessionLogin(String sessionLogin){
        Session getSession = null;
        try{
            getSession = sessionRepo.findBySession(sessionLogin);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(getSession!=null) return true;
        return false;
    }

    public Customers getCustomer(String email){
        Customers customer = null;
        try{
            customer = customerRepo.findByEmail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

    public Session SaveSession (int customerid,String sesion){
        Session saveSession = new Session();
        saveSession.setCustomerid(customerid);
        saveSession.setSessionid(sesion);
        return sessionRepo.save(saveSession);
    }

    public Role createRole(int arrayRole[]){
        Role role = new Role();
        role.setAdmin(arrayRole[0]);
        role.setBuy(arrayRole[1]);
        role.setCrud(arrayRole[2]);
        return roleRepo.save(role);
    }

    public Customers saveCustomer(Customers customers){
        return customerRepo.save(customers);
    }

}
