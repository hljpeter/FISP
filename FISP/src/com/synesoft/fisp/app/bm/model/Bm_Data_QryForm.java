package com.synesoft.fisp.app.bm.model;

import com.synesoft.fisp.domain.model.SysDataDict;
public class Bm_Data_QryForm {
	private static final long serialVersionUID = 1L;
	/*元素组号*/
	private String groupCode;
	/*元素组名*/
	private String groupName;
	/*元素名称 */
	private String metaName;
	/*元素国际化语言*/
	private String metaLan;
	private  SysDataDict sysDataDict= new SysDataDict();
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getMetaName() {
		return metaName;
	}
	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
	public String getMetaLan() {
		return metaLan;
	}
	public void setMetaLan(String metaLan) {
		this.metaLan = metaLan;
	}
	public SysDataDict getSysDataDict() {
		return sysDataDict;
	}
	public void setSysDataDict(SysDataDict sysDataDict) {
		this.sysDataDict = sysDataDict;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



    
}
