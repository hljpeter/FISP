package com.synesoft.ftzmis.app.model; 
/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public class FTZCMMetaQryForm {

	private String metaName;// 交易性质名称  groupName
	
	private String metaVal; //交易性质代码  metaVal
	
	private String selected_groupCode; 
	
	public String getSelected_groupCode() {
		return selected_groupCode;
	}

	public void setSelected_groupCode(String selected_groupCode) {
		this.selected_groupCode = selected_groupCode;
	}


	public String getMetaVal() {
		return metaVal;
	}

	public void setMetaVal(String metaVal) {
		this.metaVal = metaVal;
	}

	public String getMetaName() {
		return metaName;
	}

	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
	
	
}
 