package com.synesoft.pisa.app.draft.model;

import java.io.Serializable;

import com.synesoft.pisa.domain.model.BizDraftAcptList;

public class Draft_Paper_Form implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4000623749130757582L;

	private BizDraftAcptList bizDraftAcptList=new BizDraftAcptList();
	
	private String minDraftAmt;
	
	private String maxDraftAmt;
	
	private String remark;

	/**
	 * @return the bizDraftAcptList
	 */
	public BizDraftAcptList getBizDraftAcptList() {
		return bizDraftAcptList;
	}

	/**
	 * @param bizDraftAcptList the bizDraftAcptList to set
	 */
	public void setBizDraftAcptList(BizDraftAcptList bizDraftAcptList) {
		this.bizDraftAcptList = bizDraftAcptList;
	}

	/**
	 * @return the minDraftAmt
	 */
	public String getMinDraftAmt() {
		return minDraftAmt;
	}

	/**
	 * @param minDraftAmt the minDraftAmt to set
	 */
	public void setMinDraftAmt(String minDraftAmt) {
		this.minDraftAmt = minDraftAmt;
	}

	/**
	 * @return the maxDraftAmt
	 */
	public String getMaxDraftAmt() {
		return maxDraftAmt;
	}

	/**
	 * @param maxDraftAmt the maxDraftAmt to set
	 */
	public void setMaxDraftAmt(String maxDraftAmt) {
		this.maxDraftAmt = maxDraftAmt;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
