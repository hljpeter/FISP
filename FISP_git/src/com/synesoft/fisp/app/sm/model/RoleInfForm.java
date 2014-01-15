package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.RoleInf;

public class RoleInfForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_02_03_Modify {
	}

	
	private RoleInf roleInf;
	
	private String roleid;
	@Valid
	@NotEmpty(groups = { SM_02_03_Modify.class}, message ="{e.sm.1014}")
	private String rolename;
	
	@Valid
	@NotEmpty(groups = { SM_02_03_Modify.class}, message ="{e.sm.1004}")
	private String meunlist="";
	
	
	//显示树是否可改
	private String editable = "true";
	/**
	 * @return the meunlist
	 */
	public String getMeunlist() {
		return meunlist;
	}

	/**
	 * @param meunlist the meunlist to set
	 */
	public void setMeunlist(String meunlist) {
		this.meunlist = meunlist;
	}

	/**
	 * @return the roleInf
	 */
	public RoleInf getRoleInf() {
		return roleInf;
	}

	/**
	 * @param roleInf the roleInf to set
	 */
	public void setRoleInf(RoleInf roleInf) {
		this.roleInf = roleInf;
	}

	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename the rolename to set
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

}
