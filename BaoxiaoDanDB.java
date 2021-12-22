package com.bxd.domain;

public class BaoxiaoDanDB {
	@Override
	public String toString() {
		return "BaoxiaoDanDB [leaveId=" + leaveId + ", userId=" + userId + ", userName=" + userName + ", duration="
				+ duration + ", reason=" + reason + ", state=" + state + ", approver=" + approver + ", remark=" + remark
				+ "]";
	}
	private Integer userId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getleaveId() {
		return leaveId;
	}
	public void setleaveId(Integer leaveId) {
		leaveId = leaveId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getApprover() {
		return approver;
	}
	public void setApprover(Integer approver) {
		this.approver = approver;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private Integer leaveId;
	private String userName;
	private Double duration;
	private String reason; 
	private Integer state;
	private Integer approver;
	private String remark; 
	
	
	

}

