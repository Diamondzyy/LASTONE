package com.bxd.service;

import java.util.List;

import com.bxd.dao.BaoxiaoDanDao;
import com.bxd.domain.BaoxiaoDanDB;

public class BaoxiaoDanService {
	
	private BaoxiaoDanDao BaoxiaoDanDao = new BaoxiaoDanDao();

	public void addLeave(Integer userId,String userName,
			Double duration,String reason,Integer role) throws Exception {
		BaoxiaoDanDB BaoxiaoDanDB = new BaoxiaoDanDB();
		BaoxiaoDanDB.setUserId(userId);
		BaoxiaoDanDB.setUserName(userName);
		BaoxiaoDanDB.setDuration(duration);
		BaoxiaoDanDB.setReason(reason);
		BaoxiaoDanDB.setState(0);
		//普通员工 和部门负责人
		if (role == 0 || role == 1) {
			if (duration <= 3000) {
				//报销金额小于3000元 部门负责人
				BaoxiaoDanDB.setApprover(1); 
			}
			if (duration > 3000 && duration <= 7000) {
				//人事行政审批
				BaoxiaoDanDB.setApprover(2);
			}
			if (duration > 7000) {
				BaoxiaoDanDB.setApprover(3);
			}
		}
		//行政 审批人选择逻辑
		if (role == 2) {
			if (duration <= 7) {
				//人事行政自己审批
				BaoxiaoDanDB.setApprover(2);
			}
			if (duration > 7) {
				BaoxiaoDanDB.setApprover(3);
			}
		}
		
		//总经理
		if (role == 3) {
			BaoxiaoDanDB.setApprover(3);
		}
		BaoxiaoDanDao.addLeave(BaoxiaoDanDB);
	}
	
	//获取我的报销单
	public List<BaoxiaoDanDB> getMyList(Integer userId){
		return BaoxiaoDanDao.getMyList(userId);
	}
	//获取需要我审批的报销单
	public List<BaoxiaoDanDB> getapproveList(Integer role){
		return BaoxiaoDanDao.getApproveList(role);
	}
	
	//审批报销单
	public void changeState(Integer leaveId,Integer state){
		BaoxiaoDanDao.changeState(leaveId, state);
	}
	
	public List<BaoxiaoDanDB> getMyListByState(Integer userId){
		return BaoxiaoDanDao.getMyListByState(userId);
	}
	
	//根据leaveId进行查询
	public List<BaoxiaoDanDB> getDetail(Integer leaveId){
		return BaoxiaoDanDao.getDetail(leaveId);
	}
}
