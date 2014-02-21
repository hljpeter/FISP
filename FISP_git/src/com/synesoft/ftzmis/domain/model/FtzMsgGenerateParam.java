package com.synesoft.ftzmis.domain.model;

import java.util.List;

/**
 * 报文生成参数
 * @author yyw
 *
 */
public class FtzMsgGenerateParam {

	/** 机构ID */
	private String branchId;
	/** 机构ID List */
	private List<String> branchs;
	/** 报文编号 List */
	private List<String> msgNos;
	/** 报文编号 */
	private String msgNo;
	/** 正在录入条数 */
	private Integer inputing;
	/** 录入完成条数 */
	private Integer completed;
	/** 审核通过条数 */
	private Integer succ;
	/** 审核失败条数 */
	private Integer fail;
	/** 批量ID */
	private String msgId;
	/** 记录数 */
	private Integer cnt;
	/** 报送标志（A-新增，C-修改，D-删除） */
	private String sendFlag;
	/** 发起节点标志 */
	private String src;
	/** 待发送数量*/
	private Integer waiting;
	/** 空报文标志*/
	private String blankFlag;
	
	// 生成空报文时使用
	/** 工作日期 */
	private String workDate;
	/** 账号 */
	private String accountNo;
	/** 子账号 */
	private String subAccountNo;
	/** 申报日期 */
	private String submitDate;
	
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the subAccountNo
	 */
	public String getSubAccountNo() {
		return subAccountNo;
	}
	/**
	 * @param subAccountNo the subAccountNo to set
	 */
	public void setSubAccountNo(String subAccountNo) {
		this.subAccountNo = subAccountNo;
	}
	/**
	 * @return the submitDate
	 */
	public String getSubmitDate() {
		return submitDate;
	}
	/**
	 * @param submitDate the submitDate to set
	 */
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	/**
	 * @return the blankFlag
	 */
	public String getBlankFlag() {
		return blankFlag;
	}
	/**
	 * @param blankFlag the blankFlag to set
	 */
	public void setBlankFlag(String blankFlag) {
		this.blankFlag = blankFlag;
	}
	/**
	 * @return the msgNos
	 */
	public List<String> getMsgNos() {
		return msgNos;
	}
	/**
	 * @param msgNos the msgNos to set
	 */
	public void setMsgNos(List<String> msgNos) {
		this.msgNos = msgNos;
	}
	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the waiting
	 */
	public Integer getWaiting() {
		return waiting;
	}
	/**
	 * @param waiting the waiting to set
	 */
	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}
	/**
	 * @return the sendFlag
	 */
	public String getSendFlag() {
		return sendFlag;
	}
	/**
	 * @param sendFlag the sendFlag to set
	 */
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	/**
	 * @return the branchs
	 */
	public List<String> getBranchs() {
		return branchs;
	}
	/**
	 * @param branchs the branchs to set
	 */
	public void setBranchs(List<String> branchs) {
		this.branchs = branchs;
	}
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
	 * @return the msgNo
	 */
	public String getMsgNo() {
		return msgNo;
	}
	/**
	 * @param msgNo the msgNo to set
	 */
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	/**
	 * @return the inputing
	 */
	public Integer getInputing() {
		return inputing;
	}
	/**
	 * @param inputing the inputing to set
	 */
	public void setInputing(Integer inputing) {
		this.inputing = inputing;
	}
	/**
	 * @return the completed
	 */
	public Integer getCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	/**
	 * @return the succ
	 */
	public Integer getSucc() {
		return succ;
	}
	/**
	 * @param succ the succ to set
	 */
	public void setSucc(Integer succ) {
		this.succ = succ;
	}
	/**
	 * @return the fail
	 */
	public Integer getFail() {
		return fail;
	}
	/**
	 * @param fail the fail to set
	 */
	public void setFail(Integer fail) {
		this.fail = fail;
	}
	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}
	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	/**
	 * @return the cnt
	 */
	public Integer getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	/**
	 * @return the workDate
	 */
	public String getWorkDate() {
		return workDate;
	}
	/**
	 * @param workDate the workDate to set
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	
}