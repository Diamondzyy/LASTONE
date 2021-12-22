package com.bxd.service;

import com.bxd.dao.UserDao;
import com.bxd.domain.UserDB;

public class UserService {
	
	private UserDao userDao = new UserDao();

	//查询用户
	public UserDB getUser(String userName,String password) throws Exception {
		return userDao.getUser(userName, password);
	}
}

