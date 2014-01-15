package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.RoleInfTmp;

public class RoleInfTmpForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778429157589770868L;

	private RoleInfTmp roleInfTmp;

	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_02_02_Add {
	}
	
	@Valid
	@NotEmpty(groups = { SM_02_02_Add.class}, message ="{e.sm.1005}")
	private String roleid="";
	
	@Valid
	@NotEmpty(groups = { SM_02_02_Add.class}, message ="{e.sm.1004}")
	private String meunlist="";
	
	@Valid
	@NotEmpty(groups = { SM_02_02_Add.class}, message ="{e.sm.1014}")
	private String rolename="";
	
	//显示树是否可改
	private String editable = "true";
	
	private String opttype;

	
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
	 * @return the roleInfTmp
	 */
	public RoleInfTmp getRoleInfTmp() {
		return roleInfTmp;
	}

	/**
	 * @param roleInfTmp the roleInfTmp to set
	 */
	public void setRoleInfTmp(RoleInfTmp roleInfTmp) {
		this.roleInfTmp = roleInfTmp;
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

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
}
