package com.synesoft.ftzmis.domain.model;

public class FtzMsgBatch {

	private Long id;

	private String msgId;

	private String msgNo;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

}