package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.MysqlDao;
import com.example.demo.service.ImpalaService;
import com.example.demo.service.MysqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    // 自动注入资源
    @Autowired
    ImpalaService impalaService;
    @Autowired
    DataSourceProperties dataSourceProperties;
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MysqlService mysqlService;
    @Test

    public void contextLoads() {

        System.out.println(impalaService.query("select * from jar limit 10"));
    }

    // 测试数据源
    @org.junit.Test
    public void dataSource() {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from jar limit 10");
        System.out.println(JSON.toJSONString(list));
    }

    // 测试打他Source2 DAO
    @org.junit.Test
    public void MySQLDAO() {
        System.out.println(mysqlService.query("select * from warehouse.mar_order limit 1"));
    }

}

