package com.synesoft.ftzmis.domain.model;

public class FtzMsgProc {

	private Long id;

	private String msgId;

	private String msgNo;

	private String msgRef;
	
	private String workDate;

	private String msgDirection;

	private String msgStatus;

	private String fileName;

	private String sendTime;

	private String receiveTime;

	private String commReplyTime;

	private String procReplyTime;

	private String appProcTime;

	private Short appProcCount;

	private String failReason;

	private String rsv1;

	private String rsv2;

	private String rsv3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getMsgRef() {
		return msgRef;
	}

	public void setMsgRef(String msgRef) {
		this.msgRef = msgRef;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getMsgDirection() {
		return msgDirection;
	}

	public void setMsgDirection(String msgDirection) {
		this.msgDirection = msgDirection;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getCommReplyTime() {
		return commReplyTime;
	}

	public void setCommReplyTime(String commReplyTime) {
		this.commReplyTime = commReplyTime;
	}

	public String getProcReplyTime() {
		return procReplyTime;
	}

	public void setProcReplyTime(String procReplyTime) {
		this.procReplyTime = procReplyTime;
	}

	public String getAppProcTime() {
		return appProcTime;
	}

	public void setAppProcTime(String appProcTime) {
		this.appProcTime = appProcTime;
	}

	public Short getAppProcCount() {
		return appProcCount;
	}

	public void setAppProcCount(Short appProcCount) {
		this.appProcCount = appProcCount;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getRsv1() {
		return rsv1;
	}

	public void setRsv1(String rsv1) {
		this.rsv1 = rsv1;
	}

	public String getRsv2() {
		return rsv2;
	}

	public void setRsv2(String rsv2) {
		this.rsv2 = rsv2;
	}

	public String getRsv3() {
		return rsv3;
	}

	public void setRsv3(String rsv3) {
		this.rsv3 = rsv3;
	}
}