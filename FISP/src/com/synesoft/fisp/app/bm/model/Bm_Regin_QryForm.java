package com.synesoft.fisp.app.bm.model;


import com.synesoft.fisp.domain.model.SysRegionInfo;
public class Bm_Regin_QryForm {
	private static final long serialVersionUID = 1L;
	private String regionCode;
	private String regionInfo;
	private String regionNm;
	private  SysRegionInfo sysRegionInfo= new SysRegionInfo();

	public SysRegionInfo getSysRegionInfo() {
		return sysRegionInfo;
	}

	public void setSysRegionInfo(SysRegionInfo sysRegionInfo) {
		this.sysRegionInfo = sysRegionInfo;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionInfo() {
		return regionInfo;
	}

	public void setRegionInfo(String regionInfo) {
		this.regionInfo = regionInfo;
	}

	public String getRegionNm() {
		return regionNm;
	}

	public void setRegionNm(String regionNm) {
		this.regionNm = regionNm;
	}
    
	
	
}
