package com.example.demo.dao;

import com.alibaba.fastjson.JSON;
import com.example.demo.utils.RstoJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * created by shoujunw on ${Data}
 * 单例对象
 */
@Component
public class ImpalaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate; // 只需要注入这个就ok

    /**
     *
     * @param sql
     * @return
     * 问题:
     * 1, 如果sql写错,直接报语法错误UncategorizedSQLException
     */
    public String query(String sql) {
        try {
            return JSON.toJSONString(this.jdbcTemplate.queryForList(sql));
        }catch (Exception e){
            return "ERROR";
        }
    }
}
