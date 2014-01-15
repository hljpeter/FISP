package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.app.common.constants.UserConst;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInfTmp;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;

public class UserInfTmpForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2618928462292091315L;

	private UserInfTmp userInfTmp;
	
	private List<String> userSelectRoles;
	
	private OrgInf orgInf;
	
	private String[] roleOrgArray;

	private String[] roleArray;
	
	private List<UserOrgInfTmp> userOrgInfTempList;

	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_03_02_Add {
	}
	
	private UserOrgInfTmp userOrgInfTmp;
	@Valid
	@NotEmpty(groups = { SM_03_02_Add.class}, message ="{e.sm.1006}")
	private String userid="";
	
	@Valid
	@NotEmpty(groups = { SM_03_02_Add.class}, message ="{e.sm.1007}")
	private String loginorg="";
	
	@Valid
	@NotEmpty(groups = { SM_03_02_Add.class}, message ="{e.sm.1015}")
	private String username="";
	
	@Valid
	@NotEmpty(groups = { SM_03_02_Add.class}, message ="{e.sm.1010}")
	private List<String>  userOrgInfArr;

	/** 角色模式 */
	private String roleMode = UserConst.getROLE_MODE();
	
	private String opttype;

	
	/**
	 * @return the userOrgInfTmp
	 */
	public UserOrgInfTmp getUserOrgInfTmp() {
		return userOrgInfTmp;
	}

	/**
	 * @param userOrgInfTmp the userOrgInfTmp to set
	 */
	public void setUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp) {
		this.userOrgInfTmp = userOrgInfTmp;
	}

	/**
	 * @return the userInfTmp
	 */
	public UserInfTmp getUserInfTmp() {
		return userInfTmp;
	}

	/**
	 * @param userInfTmp the userInfTmp to set
	 */
	public void setUserInfTmp(UserInfTmp userInfTmp) {
		this.userInfTmp = userInfTmp;
	}


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
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the loginorg
	 */
	public String getLoginorg() {
		return loginorg;
	}

	/**
	 * @param loginorg the loginorg to set
	 */
	public void setLoginorg(String loginorg) {
		this.loginorg = loginorg;
	}

	/**
	 * @return the userOrgInfArr
	 */
	public List<String> getUserOrgInfArr() {
		return userOrgInfArr;
	}

	/**
	 * @param userOrgInfArr the userOrgInfArr to set
	 */
	public void setUserOrgInfArr(List<String> userOrgInfArr) {
		this.userOrgInfArr = userOrgInfArr;
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

	public List<String> getUserSelectRoles() {
		return userSelectRoles;
	}

	public void setUserSelectRoles(List<String> userSelectRoles) {
		this.userSelectRoles = userSelectRoles;
	}

	public String[] getRoleOrgArray() {
		return roleOrgArray;
	}

	public void setRoleOrgArray(String[] roleOrgArray) {
		this.roleOrgArray = roleOrgArray;
	}

	public List<UserOrgInfTmp> getUserOrgInfTempList() {
		return userOrgInfTempList;
	}

	public void setUserOrgInfTempList(List<UserOrgInfTmp> userOrgInfTempList) {
		this.userOrgInfTempList = userOrgInfTempList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the roleMode
	 */
	public String getRoleMode() {
		return roleMode;
	}

	/**
	 * @param roleMode the roleMode to set
	 */
	public void setRoleMode(String roleMode) {
		this.roleMode = roleMode;
	}

	/**
	 * @return the roleArray
	 */
	public String[] getRoleArray() {
		return roleArray;
	}

	/**
	 * @param roleArray the roleArray to set
	 */
	public void setRoleArray(String[] roleArray) {
		this.roleArray = roleArray;
	}
	
}
