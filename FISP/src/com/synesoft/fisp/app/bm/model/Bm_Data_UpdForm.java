package com.synesoft.fisp.app.bm.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


import com.synesoft.fisp.domain.model.SysDataDict;
/**
 * @author Jacky
 *
 */
public class Bm_Data_UpdForm {
	private static final long serialVersionUID = 1L;

	/*元素值*/
	@Valid
	@NotEmpty(groups = { Bm_Dict_Modify.class}, message ="{e.bm.0002}")
	private String metaVal;	
	/*元素名称 */
	@Valid
	@NotEmpty(groups = { Bm_Dict_Modify.class}, message ="{e.bm.0004}")
	private String metaName;
	
	/*修改之前的元素名  记录日志*/
	private String beforeMetaName;
	/*修改之前的元素之  记录日志*/
	private String beforeMetaVal;
	
	private  SysDataDict sysDataDict= new SysDataDict();
	

   public static interface Bm_Dict_Modify{
		
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


	public String getBeforeMetaName() {
		return beforeMetaName;
	}


	public void setBeforeMetaName(String beforeMetaName) {
		this.beforeMetaName = beforeMetaName;
	}


	public String getBeforeMetaVal() {
		return beforeMetaVal;
	}


	public void setBeforeMetaVal(String beforeMetaVal) {
		this.beforeMetaVal = beforeMetaVal;
	}
	
	    
}
