package com.synesoft.ftzmis.app.model; 
/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public class FTZCMNationQryForm {

	private String nationShortName; //国家简称
	
	private String nationCode;	//国家字母代码
	
	private String nationNumThree;	//国家数字代码
	
	private String selected_nationCode="";
	private String selected_nationNumThree="";
	private String selected_nationShortName="";

	public String getNationShortName() {
		return nationShortName;
	}

	public void setNationShortName(String nationShortName) {
		this.nationShortName = nationShortName;
	}

	
	public String getNationNumThree() {
		return nationNumThree;
	}

	public void setNationNumThree(String nationNumThree) {
		this.nationNumThree = nationNumThree;
	}

	
	public String getSelected_nationNumThree() {
		return selected_nationNumThree;
	}

	public void setSelected_nationNumThree(String selected_nationNumThree) {
		this.selected_nationNumThree = selected_nationNumThree;
	}

	public String getSelected_nationShortName() {
		return selected_nationShortName;
	}

	public void setSelected_nationShortName(String selected_nationShortName) {
		this.selected_nationShortName = selected_nationShortName;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getSelected_nationCode() {
		return selected_nationCode;
	}

	public void setSelected_nationCode(String selected_nationCode) {
		this.selected_nationCode = selected_nationCode;
	}
	
	
}
 