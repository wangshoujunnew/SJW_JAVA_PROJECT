package com.example.demo.service;

import com.example.demo.dao.ImpalaDao;
import com.example.demo.dao.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by shoujunw on ${Data}
 */
@Service
public class MysqlService {

    @Autowired
    MysqlDao mysqlDao;

    public String query(String sql){
        return this.mysqlDao.query(sql);
    }
}
