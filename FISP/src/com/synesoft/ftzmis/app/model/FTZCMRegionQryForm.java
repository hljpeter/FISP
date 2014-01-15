package com.synesoft.ftzmis.app.model;

public class FTZCMRegionQryForm {

	
	private String regionInfo ="";  //行政区划名称
	
	private String regionNum ="";   //行政区划代码
	
	private String selected_regionInfo;
	
	private String selected_regionNum;
	
	
	
	public String getRegionInfo() {
		return regionInfo;
	}

	public void setRegionInfo(String regionInfo) {
		this.regionInfo = regionInfo;
	}

	public String getRegionNum() {
		return regionNum;
	}

	public void setRegionNum(String regionNum) {
		this.regionNum = regionNum;
	}

	public String getSelected_regionInfo() {
		return selected_regionInfo;
	}

	public void setSelected_regionInfo(String selected_regionInfo) {
		this.selected_regionInfo = selected_regionInfo;
	}

	public String getSelected_regionNum() {
		return selected_regionNum;
	}

	public void setSelected_regionNum(String selected_regionNum) {
		this.selected_regionNum = selected_regionNum;
	}
}
