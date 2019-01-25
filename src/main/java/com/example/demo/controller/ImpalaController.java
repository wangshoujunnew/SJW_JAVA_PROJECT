package com.example.demo.controller;

import com.example.demo.dao.MysqlDao;
import com.example.demo.service.ImpalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * created by shoujunw on ${Data}
 * impala语句查询
 */
@Controller
public class ImpalaController {


    @Autowired
    ImpalaService impalaService;

    @Autowired
    MysqlDao mysqlDao;

    @ResponseBody
    @RequestMapping("/query")
    public String select(String sql, HttpServletRequest request, HttpServletResponse response) {
        // 将查询记录存入数据库中
        String remoteHost = request.getRemoteHost();
        System.out.println(remoteHost);
        mysqlDao.insert(remoteHost + ":" + sql);

        // 如果sql是以select开头的,那么我只查出前50条sql
        if(sql.trim().startsWith("select")) {
            sql = "select * from ({1}) ttttttttttt limit 50".replace("{1}", sql);
        }
        return impalaService.query(sql);
    }

    // sql查询页面
    @RequestMapping("/sql_test")
    public ModelAndView sql_test(){
        return new ModelAndView("sql_test");
    }

    // 得到表结构
    @RequestMapping("/table_desc")
    public ModelAndView table_desc(){
        return new ModelAndView("table_desc");
    }

    // 导航页面
    @RequestMapping("/navigat")
    public ModelAndView navigat(){
        return new ModelAndView("navigate");
    }

    // 查询表结构, 直接查sql就可以query?sql=desc tables

    // 1. 测试模版引擎, Yes
    // 2. 并放入变量 Yes
    // 3. 测试样式加入到html中 Yes
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();

        mv.addObject("name", "shoujuw");
        mv.setViewName("index");
        return mv;
    }

}
