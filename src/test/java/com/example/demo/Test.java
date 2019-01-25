package com.example.demo;

import com.example.demo.dao.ImpalaDao;
import com.example.demo.dao.MysqlDao;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.demo.utils.RstoJson;
import org.junit.After;

/**
 * created by shoujunw on ${Data}
 * 测试类
 */
public class Test {

    // Mysql版本问题引起的故障
    @org.junit.Test
    public void test() { // 测试链接mysql
        String url = "jdbc:mysql://10.2.72.38:3306/test";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "123456";
        Connection con = null;
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, username, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from jar limit 10";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            // 将ResultSet转为json对象
            System.out.println(RstoJson.resultSetToJsonArry(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 运行spark, spark版本问题,运行失败
    @org.junit.Test
    public void spark_run() {
        SparkConf sparkConf = new SparkConf()
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .set("hive.metastore.uris", "thrift://172.31.86.16:9083")
                .set("hive.metastore.warehouse.dir", "hdfs://172.31.86.25/home/data/hive/warehouse");
        SQLContext session = new SQLContext(new SparkContext(sparkConf));
        System.out.println(session);

    }

    // 运行impala查询数据, 运行成功
    @org.junit.Test
    public void impala_run() {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Connection con = DriverManager.getConnection("jdbc:hive2://172.31.86.16:21050/;auth=noSasl");
            ResultSet resultSet = con.createStatement().executeQuery("select checkin_date from warehouse.mar_order limit 10");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试Dao方法, 成功
    @org.junit.Test
    public void daoTest() {
        ImpalaDao impalaDao = new ImpalaDao();
        System.out.println(impalaDao.query("select * from jar limit 10"));
    }

    // 测试项数据库中插入数据
    @org.junit.Test
    public void daoInsertTest() {
        MysqlDao mysqlDao = new MysqlDao();
        System.out.println(mysqlDao.insert("select * from jar limit 10"));
    }


    @org.junit.Test
    public void demo() {
        String sql = "select * from   warheouse.mar_order";
        sql = "select * from ({1}) ttttttttttt limit 50".replace("{1}", sql);
        //        System.out.println(sql);
        //        System.out.printf("%s","中国人");
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        List<String> collect = list.stream().map(item -> {
            return item + "shoujunw";
        }).filter(item -> {
            return item.startsWith("a");
        }).collect(Collectors.toList());
        System.out.println(collect);
        int a = 100;
    }


}
