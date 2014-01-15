package com.synesoft.fisp.app.sm.model;

import com.synesoft.fisp.domain.model.SysParam;

public class SysParamForm {
	
	private SysParam sysParam;
	
	private String del_paramInfo;
	
	private String mod_paramInfo;
	
	private String detail_modify_flag;

	public String getDel_paramInfo() {
		return del_paramInfo;
	}

	public void setDel_paramInfo(String del_paramInfo) {
		this.del_paramInfo = del_paramInfo;
	}

	public String getMod_paramInfo() {
		return mod_paramInfo;
	}

	public void setMod_paramInfo(String mod_paramInfo) {
		this.mod_paramInfo = mod_paramInfo;
	}

	public SysParam getSysParam() {
		return sysParam;
	}

	public void setSysParam(SysParam sysParam) {
		this.sysParam = sysParam;
	}

	public String getDetail_modify_flag() {
		return detail_modify_flag;
	}

	public void setDetail_modify_flag(String detail_modify_flag) {
		this.detail_modify_flag = detail_modify_flag;
	}
	
}
