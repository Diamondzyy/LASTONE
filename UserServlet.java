package com.bxd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bxd.domain.UserDB;
import com.bxd.service.UserService;

/**
 *  * 用户控制层
 *   * @author Administrator
 *    *
 *     */
@SuppressWarnings(value="all")
@WebServlet(urlPatterns="/userServlet")
public class UserServlet extends HttpServlet{
	
	private UserService userService = new UserService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			String function = request.getParameter("function");
			if ("login".equals(function)) {
				login(request, response, session);
			}
			if ("logOut".equals(function)) {
				logOut(request, response, session);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	void login(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		try {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			UserDB userDB = userService.getUser(userName, password);
			if (userDB == null) {
				//账号密码不正确
				session.setAttribute("msg", "登陆失败");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} else {
				session.setAttribute("msg", "");
				session.setAttribute("userDB", userDB);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//退出系统
	void logOut(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		session.invalidate();
		System.out.println(request.getContextPath());
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}
