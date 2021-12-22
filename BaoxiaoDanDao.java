package com.bxd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.bxd.domain.BaoxiaoDanDB;
import com.bxd.utils.DBUtils;

public class BaoxiaoDanDao{
        public List<BaoxiaoDanDB> getMyList(Integer userId) {
		//sql语句
		String sql = "select * from tb_bxd where user_id = ?";
		Object[] objects = {userId};
		return list(sql, objects);
	}
        
        public List<BaoxiaoDanDB> list(String sql,Object[] objects) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			//获取连接
			conn = DBUtils.getConnection();
			//sql语句
			String sqls = sql;
			psmt = conn.prepareStatement(sqls);
			//参数
			for (int i = 0; i < objects.length; i++) {
				psmt.setObject(i+1, objects[i]);
			}
			rs = psmt.executeQuery();
			List<BaoxiaoDanDB> list = new ArrayList<>();
			while(rs.next()) {
				BaoxiaoDanDB BaoxiaoDanDB = new BaoxiaoDanDB();
				BaoxiaoDanDB.setleaveId(rs.getInt("bxd_id"));
				BaoxiaoDanDB.setUserId(rs.getInt("user_id"));
				BaoxiaoDanDB.setUserName(rs.getString("user_name"));
				BaoxiaoDanDB.setDuration(rs.getDouble("duration"));
				BaoxiaoDanDB.setReason(rs.getString("reason"));
				BaoxiaoDanDB.setState(rs.getInt("state"));
				BaoxiaoDanDB.setApprover(rs.getInt("approver"));
				list.add(BaoxiaoDanDB);
			}
                        return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, psmt, conn);
		}
		return null;
	}

        //获取我的未审批报销单
        public List<BaoxiaoDanDB> getMyListByState(Integer userId) {
		String sql = "select * from tb_bxd where user_id = ? and state = 0";
		Object[] objects = {userId};
		return list(sql, objects);
	}
	public List<BaoxiaoDanDB> getApproveList(Integer role) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
                         //获取连接
                         conn = DBUtils.getConnection();
			//sql语句
			String sql = "select * from tb_bxd where approver = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setObject(1, role);
			rs = psmt.executeQuery();
			List<BaoxiaoDanDB> list = new ArrayList<>();
			while(rs.next()) {
				BaoxiaoDanDB BaoxiaoDanDB = new BaoxiaoDanDB();
				BaoxiaoDanDB.setleaveId(rs.getInt("bxd_id"));
				BaoxiaoDanDB.setUserId(rs.getInt("user_id"));
				BaoxiaoDanDB.setUserName(rs.getString("user_name"));
				BaoxiaoDanDB.setDuration(rs.getDouble("duration"));
				BaoxiaoDanDB.setReason(rs.getString("reason"));
				BaoxiaoDanDB.setState(rs.getInt("state"));
				BaoxiaoDanDB.setApprover(rs.getInt("approver"));
				list.add(BaoxiaoDanDB);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, psmt, conn);
		}
		return null;
	}
	
	//修改审批状态
	public void changeState(Integer leaveId,Integer state) {
		String sql = "update tb_bxd set state = ? where bxd_id = ?";
		Object[] objects = {state,leaveId};
		myUpdate(sql, objects);
	}
	
	public void addLeave(BaoxiaoDanDB BaoxiaoDanDB){
		String sql = "insert into tb_bxd "
				+ "(user_id,user_name,duration,reason,state,approver) "
				+ "values (?,?,?,?,?,?)";
		Object[] objects = {BaoxiaoDanDB.getUserId(),BaoxiaoDanDB.getUserName(),BaoxiaoDanDB.getDuration(),BaoxiaoDanDB.getReason(),BaoxiaoDanDB.getState(),BaoxiaoDanDB.getApprover()};
		myUpdate(sql, objects);
	}
	
	//修改方法
	void myUpdate(String sql,Object...objects) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			//获取连接
			conn = DBUtils.getConnection();
			String sqls = sql;
			psmt = conn.prepareStatement(sqls);
			//参数
			for (int i = 0; i < objects.length; i++) {
				psmt.setObject(i+1, objects[i]);
			}			
			psmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeAll(null, psmt, conn);
		}
	}
	
	public List<BaoxiaoDanDB> getDetail(Integer leaveId){
		String sql = "select * from tb_bxd where bxd_id = ?";
		Object[] objects = {leaveId};
		return list(sql, objects);
	}
}
