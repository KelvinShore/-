package com.atguigu.ct.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kelvin on 2019/5/31.
 */
public class JDBCUtil {

    private static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://hadoop102:3306/ct190105?useUnicode=true&characterEncoding=UTF-8";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "000000";

    public static Connection getConnection(){
        Connection conn=null;

        try {
            Class.forName(MYSQL_DRIVER_CLASS);
            conn= DriverManager.getConnection(MYSQL_URL,MYSQL_USERNAME,MYSQL_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }



}
