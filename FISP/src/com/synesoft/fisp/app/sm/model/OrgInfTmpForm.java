package com.synesoft.fisp.app.sm.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.OrgInfTmp;

public class OrgInfTmpForm {
	
	private OrgInfTmp orgInfTmp;

	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_01_02_Add {
	}
	
	@Valid
	@NotEmpty(groups = { SM_01_02_Add.class}, message ="{e.sm.1001}")
	private String orgid="";
	
	@Valid
	@NotEmpty(groups = { SM_01_02_Add.class}, message ="{e.sm.1016}")
	private String orgname="";
	
//	@Valid
//	@NotEmpty(groups = { SM_01_02_Add.class}, message ="{e.sm.1002}")
	private String suprorgid="";
	
//	@Valid
//	@NotEmpty(groups = { SM_01_02_Add.class}, message ="{e.sm.1003}")
	private String bankid="";
	
	private String opttype;
	
	/**
	 * @return the orgInfTmp
	 */
	public OrgInfTmp getOrgInfTmp() {
		return orgInfTmp;
	}

	/**
	 * @param orgInfTmp the orgInfTmp to set
	 */
	public void setOrgInfTmp(OrgInfTmp orgInfTmp) {
		this.orgInfTmp = orgInfTmp;
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

	/**
	 * @return the opttype
	 */
	public String getOpttype() {
		return opttype;
	}

	/**
	 * @param opttype the opttype to set
	 */
	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
}
