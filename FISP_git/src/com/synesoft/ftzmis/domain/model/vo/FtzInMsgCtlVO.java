package com.synesoft.ftzmis.domain.model.vo;

import org.hibernate.validator.constraints.NotEmpty;


import com.synesoft.ftzmis.app.model.FTZ210206Form.FTZInFormMsgQry;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;


public class FtzInMsgCtlVO extends FtzInMsgCtl {

    /** 工作日期（开始日期）*/
   // @NotEmpty(groups = {FTZInFormMsgQry.class }, message = "{e.ftzmis.2103.0003}")
	private String startDate;

    /** 工作日期（结束日期）*/
  //  @NotEmpty(groups = {FTZInFormMsgQry.class }, message = "{e.ftzmis.2103.0004}")
	private String endDate;

    /** 总记录数 */
    private String totalNum;

    /** 待审核记录数 */
    private String aprNum; 
    
    /** 报文状态组 */
    private String[] msgStatuss;
    
    /** 原报文状态 */
    private String oldMsgStatus;
    
    private String[]branchIds;
    
	public String[] getBranchIds() {
		return branchIds;
	}

	public void setBranchIds(String[] branchIds) {
		this.branchIds = branchIds;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	


	public String getMakUser() {
		return makUser;
	}

	public void setMakUser(String makUser) {
		this.makUser = makUser;
	}

	private String department;
    
    private String makUser;

	/**
	 * @return the oldMsgStatus
	 */
	public String getOldMsgStatus() {
		return oldMsgStatus;
	}

	/**
	 * @param oldMsgStatus the oldMsgStatus to set
	 */
	public void setOldMsgStatus(String oldMsgStatus) {
		this.oldMsgStatus = oldMsgStatus;
	}

	/**
	 * @return the msgStatuss
	 */
	public String[] getMsgStatuss() {
		return msgStatuss;
	}

	/**
	 * @param msgStatuss the msgStatuss to set
	 */
	public void setMsgStatuss(String[] msgStatuss) {
		this.msgStatuss = msgStatuss;
	}

	/**
	 * @return the totalNum
	 */
	public String getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the aprNum
	 */
	public String getAprNum() {
		return aprNum;
	}

	/**
	 * @param aprNum the aprNum to set
	 */
	public void setAprNum(String aprNum) {
		this.aprNum = aprNum;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
