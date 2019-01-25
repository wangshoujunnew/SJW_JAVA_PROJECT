package com.example.demo.service;

import com.example.demo.dao.ImpalaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by shoujunw on ${Data}
 */
@Service
public class ImpalaService {

    @Autowired
    ImpalaDao impalaDao;

    public String query(String sql){
        return this.impalaDao.query(sql);
    }
}
