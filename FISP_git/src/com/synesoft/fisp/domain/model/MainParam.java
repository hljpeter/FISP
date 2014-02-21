package com.synesoft.fisp.domain.model;

import java.util.List;

public class MainParam {
	
	private String tabName;
	private Integer cnt;
	private String flag;
	private List<String> msgStatusList;
	private List<String> msgNoList;
	private List<String> branchIdList;
	
	/**
	 * @return the tabName
	 */
	public String getTabName() {
		return tabName;
	}
	/**
	 * @param tabName the tabName to set
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
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
	 * @return the msgStatusList
	 */
	public List<String> getMsgStatusList() {
		return msgStatusList;
	}
	/**
	 * @param msgStatusList the msgStatusList to set
	 */
	public void setMsgStatusList(List<String> msgStatusList) {
		this.msgStatusList = msgStatusList;
	}
	/**
	 * @return the msgNoList
	 */
	public List<String> getMsgNoList() {
		return msgNoList;
	}
	/**
	 * @param msgNoList the msgNoList to set
	 */
	public void setMsgNoList(List<String> msgNoList) {
		this.msgNoList = msgNoList;
	}
	/**
	 * @return the branchIdList
	 */
	public List<String> getBranchIdList() {
		return branchIdList;
	}
	/**
	 * @param branchIdList the branchIdList to set
	 */
	public void setBranchIdList(List<String> branchIdList) {
		this.branchIdList = branchIdList;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}