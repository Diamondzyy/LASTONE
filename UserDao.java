package com.bxd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bxd.domain.UserDB;
import com.bxd.utils.DBUtils;

public class UserDao {
	
	public UserDB getUser(String userName,String password) throws Exception {
		Connection conn = DBUtils.getConnection();
		String sql = "select * from tb_user where user_name = ? and password = ?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setObject(1, userName);
		psmt.setObject(2, password);
		ResultSet rs = psmt.executeQuery();
		if (rs.next()) {
			UserDB userDB = new UserDB();
			userDB.setUserId(rs.getInt("user_id"));
			userDB.setUserName(rs.getString("user_name"));
			userDB.setPassword(rs.getString("password"));
			userDB.setRole(rs.getInt("role"));
			System.out.println(userDB);
			return userDB;
		}
		return null;
	}

}
