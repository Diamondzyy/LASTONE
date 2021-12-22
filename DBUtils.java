package com.bxd.untils;
/**
 * 数据库连接工具
 * @author Administrator
 *
 */

import java.sql.Conncetion;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

        private DBUtils(){

        }

        public static Connection getConnection() {
                try {
                       //数据库连接地址
                       String url = "jdbc:mysql://127.0.0.1:3306/sxt?characterEncoding=utf-8";
                       //用户
                       String user = "root";
                       //密码
                       String password = "930601Zyy@";
                       //加载驱动
                       Class.forName("com.mysql.jdbc.Driver");
                       //获取连接
                       return DriverManager.getConnection(url, user, password);
                } catch (Exception e) {
                            e.printStackTrace();
                  }
                  return null;
        }

        public static void main(String[] args) throws SQLException {
             Connection con =getConnection();
             String sql = "select * from tb_user where user_id = ?";
             PreparedStatement pstm = con.prepareStatement(sql);
             pstm.setObject(1, 2);
             ResultSet rs =  pstm.executeQuery();
             if (rs.next()) {

                         System.out.println(rs.getString("user_name"));
             }
        }

        public static void closeAll(ResultSet rs,PreparedStatement pstm,Connection conn) {
              if(rs != null){
                         try {
                                 rs.close();
                         } catch (SQLException e) {
                                 e.printStackTrace();
                         }
               }
               if(pstm != null) {
                         try{
                               pstm.close();
                         } catch (SQLException e) {
                                     e.printStackTrace();
                         }
               }
               if(conn != null) {
                         try{
                                conn.close();
                         } catch (SQLException e) {
                                     e.printStackTrace();
                         }
               }
        }
}
