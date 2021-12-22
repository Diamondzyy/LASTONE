package com.bxd.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bxd.domain.BaoxiaoDanDB;
import com.bxd.domain.UserDB;
import com.bxd.service.BaoxiaoDanService;
/**
 *  * 请假单控制层
 *   * @author Administrator
 *    *
 *     */
@WebServlet(urlPatterns="/BaoxiaoDanServlet")
public class BaoxiaoDanServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private BaoxiaoDanService leaveSerice = new BaoxiaoDanService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			//处理post乱码
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			String function = request.getParameter("function");
			if ("addLeave".equals(function)) {
				addLeave(request,response,session);
			}
			if ("getMyList".equals(function)) {
				getMyList(request, response, session);
			}
			if ("getApproveList".equals(function)) {
				getApproveList(request, response, session);
			}
			if ("changeState".equals(function)) {
				changeState(request, response, session);
			}
			if ("getDetail".equals(function)) {
				getDetail(request, response, session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void addLeave(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		//请假时长
		String duration = request.getParameter("dutation");
		//请假原因
		String reason = request.getParameter("reason");
		//获取当前用户
		UserDB userDB = (UserDB) session.getAttribute("userDB");
		//获取当前用户ID
		Integer userId= userDB.getUserId();
		//获取当前用户姓名
		String userName = userDB.getUserName();
		//获取当前用户的角色
		Integer role = userDB.getRole(); 
		//查询是否存在我的未审批的请假单
		List<BaoxiaoDanDB> list = leaveSerice.getMyListByState(userId);
		if (list.size() > 0) {
			request.setAttribute("message", "否存未审批的请假单");
			request.getRequestDispatcher("/addLeave.jsp").forward(request, response);
		} else {
			//添加操作
			leaveSerice.addLeave(userId, userName, Double.parseDouble(duration), reason, role);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
	}
	
	//获取我的请假单
	void getMyList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		UserDB userDB = (UserDB) session.getAttribute("userDB");
		if (userDB == null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		Integer userId = userDB.getUserId();
		List<BaoxiaoDanDB> myList = leaveSerice.getMyList(userId);
		request.setAttribute("myList", myList);
		request.getRequestDispatcher("/list.jsp").forward(request, response);
	}
	
	//需要我审批的请假单
	void getApproveList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		UserDB userDB = (UserDB) session.getAttribute("userDB");
		if (userDB != null) {
			Integer role = userDB.getRole();
			List<BaoxiaoDanDB> approveList = leaveSerice.getapproveList(role);
			System.out.println(approveList);
			request.setAttribute("approveList", approveList);
			request.getRequestDispatcher("/approveList.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		
	}
	
	//请假单审批操作
	void changeState(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		String leaveId = request.getParameter("leaveId");
		String state = request.getParameter("state");
		System.out.println("================================");
		System.out.println(leaveId);
		System.err.println(state);
		leaveSerice.changeState(Integer.parseInt(leaveId), Integer.parseInt(state));
		getApproveList(request, response, session);
	}
	
	void getDetail(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException{
		String leaveId =  request.getParameter("leaveId");
		List<BaoxiaoDanDB> details = leaveSerice.getDetail(Integer.parseInt(leaveId));
		request.setAttribute("detail", details.get(0));
		request.getRequestDispatcher("/updateLeave.jsp").forward(request, response);
	}

}
