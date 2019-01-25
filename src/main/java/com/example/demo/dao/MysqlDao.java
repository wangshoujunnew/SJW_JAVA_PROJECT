package com.example.demo.dao;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * created by shoujunw on ${Data}
 * 单例对象
 */
//@Scope("singleton")
@Repository
public class MysqlDao {
    @Autowired
    private JdbcTemplate jdbcTemplate; // 只需要注入这个就ok

    public String query(String sql) {
        return JSON.toJSONString(this.jdbcTemplate.queryForList(sql));
    }

    // 插入数据到数据库中,数据为IP:SQL
    public int insert(String log) {
//        String sql = "insert into select_log(log) values(?)";
//        return this.jdbcTemplate.update(sql, new Object[]{log});
        return 0;

    }
}
