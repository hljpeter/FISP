package com.synesoft.fisp.app.bm.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.SysOperLog;



public class BM_Oper_LogForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SysOperLog sysOperLog=new SysOperLog();
	
	/**
	 * 最小操作日期
	 */
	private String minOperDate;
	
	/**
	 * 最大操作日期
	 */
	private String maxOperDate;
	
	/**
	 * 最小操作时间
	 */
	private String minOperTime;
	
	/**
	 * 最大操作时间
	 */
	private String maxOperTime;

	/**
	 * 机构ID
	 */
	private String branchId;
	
	/**
	 * 功能名称
	 */
	private String funcName;
	
	/**
	 * 操作类型
	 */
	private String operType;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}



	/**
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}

	/**
	 * @param funcName the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	/**
	 * @return the operType
	 */
	public String getOperType() {
		return operType;
	}

	/**
	 * @param operType the operType to set
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}

	/**
	 * @return the sysOperLog
	 */
	public SysOperLog getSysOperLog() {
		return sysOperLog;
	}

	/**
	 * @param sysOperLog the sysOperLog to set
	 */
	public void setSysOperLog(SysOperLog sysOperLog) {
		this.sysOperLog = sysOperLog;
	}

	/**
	 * @return the minOperDate
	 */
	public String getMinOperDate() {
		return minOperDate;
	}

	/**
	 * @param minOperDate the minOperDate to set
	 */
	public void setMinOperDate(String minOperDate) {
		this.minOperDate = minOperDate;
	}

	/**
	 * @return the maxOperDate
	 */
	public String getMaxOperDate() {
		return maxOperDate;
	}

	/**
	 * @param maxOperDate the maxOperDate to set
	 */
	public void setMaxOperDate(String maxOperDate) {
		this.maxOperDate = maxOperDate;
	}

	/**
	 * @return the minOperTime
	 */
	public String getMinOperTime() {
		return minOperTime;
	}

	/**
	 * @param minOperTime the minOperTime to set
	 */
	public void setMinOperTime(String minOperTime) {
		this.minOperTime = minOperTime;
	}

	/**
	 * @return the maxOperTime
	 */
	public String getMaxOperTime() {
		return maxOperTime;
	}

	/**
	 * @param maxOperTime the maxOperTime to set
	 */
	public void setMaxOperTime(String maxOperTime) {
		this.maxOperTime = maxOperTime;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


}
