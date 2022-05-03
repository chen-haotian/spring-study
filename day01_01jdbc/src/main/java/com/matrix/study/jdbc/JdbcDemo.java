package com.matrix.study.jdbc;

import java.sql.*;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-03 23:58:33
 * @Description: TODO jdbc程序案例说明程序之间的耦合
 * @Version: TODO
 */
public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
//        DriverManager.deregisterDriver(new Driver());
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:13306/spring5_super?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true", "root", "cjw123456");
        // 3.获取操作数据库的预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement("select * from tb_account");
        // 4.执行SQL获取结果集
        ResultSet resultSet = preparedStatement.executeQuery();
        // 5.遍历结果集
        while (resultSet.next()) {
            System.out.println("id = " + resultSet.getInt(1) + "，name = " + resultSet.getString(2) + "，money = " + resultSet.getDouble(3));
        }
        // 6.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
