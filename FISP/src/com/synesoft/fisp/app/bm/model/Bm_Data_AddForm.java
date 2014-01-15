package com.synesoft.fisp.app.bm.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


import com.synesoft.fisp.domain.model.SysDataDict;
/**
 * @author Jacky
 *
 */
public class Bm_Data_AddForm {
	private static final long serialVersionUID = 1L;

	/*元素值*/
	@Valid
	@NotEmpty(groups = { Bm_Dict_Add.class}, message ="{e.bm.0002}")
	private String metaVal;	
	/*元素名称 */
	@Valid
	@NotEmpty(groups = { Bm_Dict_Add.class}, message ="{e.bm.0004}")
	private String metaName;
	@Valid
	@NotEmpty(groups = { Bm_Dict_Add.class}, message ="{e.bm.0001}")
	private String groupCode;
	@Valid
	@NotEmpty(groups = { Bm_Dict_Add.class}, message ="{e.bm.0003}")
	private String metaLan;	
	private String metaDesc;	
	@Valid
	@NotEmpty(groups = { Bm_Dict_Add.class}, message ="{e.bm.0005}")
	private String groupName;
	
	private  SysDataDict sysDataDict= new SysDataDict();
	

   public static interface Bm_Dict_Add{
		
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
	
	
	public SysDataDict getSysDataDict() {
		return sysDataDict;
	}
	
	
	public void setSysDataDict(SysDataDict sysDataDict) {
		this.sysDataDict = sysDataDict;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getGroupCode() {
		return groupCode;
	}


	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}


	public String getMetaLan() {
		return metaLan;
	}


	public void setMetaLan(String metaLan) {
		this.metaLan = metaLan;
	}


	public String getMetaDesc() {
		return metaDesc;
	}


	public void setMetaDesc(String metaDesc) {
		this.metaDesc = metaDesc;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
	


	    
}
