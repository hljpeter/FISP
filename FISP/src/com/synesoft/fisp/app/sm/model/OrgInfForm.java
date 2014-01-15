package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.OrgInf;

public class OrgInfForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrgInf orgInf;
	
	private String orgid;
	
	
	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_01_03_Modify {
	}
	
//	@Valid
//	@NotEmpty(groups = { SM_01_03_Modify.class}, message ="{e.sm.1002}")
	private String suprorgid="";
	
//	@Valid
//	@NotEmpty(groups = { SM_01_03_Modify.class}, message ="{e.sm.1003}")
	private String bankid="";
	
	@Valid
	@NotEmpty(groups = { SM_01_03_Modify.class}, message ="{e.sm.1016}")
	private String orgname="";
	/**
	 * @return the orgInf
	 */
	public OrgInf getOrgInf() {
		return orgInf;
	}
	/**
	 * @param orgInf the orgInf to set
	 */
	public void setOrgInf(OrgInf orgInf) {
		this.orgInf = orgInf;
	}
	/**
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}
	/**
	 * @param orgid the orgid to set
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	/**
	 * @return the orgname
	 */
	public String getOrgname() {
		return orgname;
	}
	/**
	 * @param orgname the orgname to set
	 */
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public String getSuprorgid() {
		return suprorgid;
	}
	public void setSuprorgid(String suprorgid) {
		this.suprorgid = suprorgid;
	}
	/**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	/**
	 * @param bankid the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
}
