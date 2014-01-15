package com.synesoft.fisp.app.al.model;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.SysAlertRcpt;

public class SysAlertRcptForm implements Serializable{

	public interface AL_01_01 {

	}

	
	private static final long serialVersionUID = 3551622198691893883L;

	@Valid
	@NotEmpty(groups = { AL_01_01.class}, message ="{e.al.1001}")
	private String alertType="";
	
	
	@Valid
	@NotEmpty(groups = { AL_01_01.class}, message ="{e.al.1002}")
	@Size(groups={ AL_01_01.class},min=1,max=20,message="{e.al.1006}")
	private String branchId="";
	
	@Valid
	@NotEmpty(groups = { AL_01_01.class}, message ="{e.al.1003}")
	private String noticeMthd="";
	
	@Valid
	@NotEmpty(groups = { AL_01_01.class}, message ="{e.al.1004}")
	@Size(groups={ AL_01_01.class},min=1,max=32,message="{e.al.1007}")
	private String projId="";
	
	@Valid
	@NotEmpty(groups = { AL_01_01.class}, message ="{e.al.1005}")
	private String rcptAddr="";
	
	
	
	
	
	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getNoticeMthd() {
		return noticeMthd;
	}

	public void setNoticeMthd(String noticeMthd) {
		this.noticeMthd = noticeMthd;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getRcptAddr() {
		return rcptAddr;
	}

	public void setRcptAddr(String rcptAddr) {
		this.rcptAddr = rcptAddr;
	}

	public SysAlertRcpt getSysAlertRcpt() {
		return sysAlertRcpt;
	}

	public void setSysAlertRcpt(SysAlertRcpt sysAlertRcpt) {
		this.sysAlertRcpt = sysAlertRcpt;
	}

	private SysAlertRcpt sysAlertRcpt;
}
