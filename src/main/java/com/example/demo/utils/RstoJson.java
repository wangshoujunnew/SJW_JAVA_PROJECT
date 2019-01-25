package com.example.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * created by shoujunw on ${Data}
 */
public class RstoJson {
    /**
     * 将resultSet转化为JSON数组
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public static JSONArray resultSetToJsonArry(ResultSet rs) throws SQLException, JSONException {
// json数组
        JSONArray array = new JSONArray();
// 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
// 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
// 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }

            array.add(jsonObj);
        }
        return array;
    }

    /**
     * 将resultSet转化为JSONObject
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public static JSONObject resultSetToJsonObject(ResultSet rs) throws SQLException, JSONException {
// json对象
        JSONObject jsonObj = new JSONObject();
// 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
// 遍历ResultSet中的每条数据
        if (rs.next()) {
// 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
        }
        return jsonObj;
    }
}
