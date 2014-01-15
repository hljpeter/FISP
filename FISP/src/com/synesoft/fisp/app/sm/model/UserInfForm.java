package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.app.common.constants.UserConst;
import com.synesoft.fisp.domain.model.UserInf;

public class UserInfForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserInf userInf;
	
	@Valid
	@NotEmpty(groups = { SM_03_08_Check.class}, message ="{e.sm.1006}")
	private String userid;
	
	private Map<String,String> roleOrgMap;
	
	private List<String> roleOrgList;
	
	private String orgid;
	
	private String[] roleOrgArray;
	
	private List<String> userSelectRoles;
	
	private String newPwd;
	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_03_03_Modify {
	}
	public static interface SM_03_08_Check {
	}
	
	@Valid
	@NotEmpty(groups = { SM_03_03_Modify.class}, message ="{e.sm.1007}")
	private String loginorg="";
	
	@Valid
	@NotEmpty(groups = { SM_03_03_Modify.class}, message ="{e.sm.1015}")
	private String username="";
	
//	@Valid
//	@NotEmpty(groups = { SM_03_03_Modify.class}, message ="{e.sm.1009}")
	private String userstatus="";
	
	@Valid
	@NotEmpty(groups = { SM_03_03_Modify.class}, message ="{e.sm.1010}")
	private List<String>  userOrgInfArr;

	/** 角色模式 */
	private String roleMode = UserConst.getROLE_MODE();

	private String[] roleArray;
	
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
	 * @return the userInf
	 */
	public UserInf getUserInf() {
		return userInf;
	}
	/**
	 * @param userInf the userInf to set
	 */
	public void setUserInf(UserInf userInf) {
		this.userInf = userInf;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the userstatus
	 */
	public String getUserstatus() {
		return userstatus;
	}
	/**
	 * @param userstatus the userstatus to set
	 */
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
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
	
	public Map<String, String> getRoleOrgMap() {
		return roleOrgMap;
	}
	
	public void setRoleOrgMap(Map<String, String> roleOrgMap) {
		this.roleOrgMap = roleOrgMap;
	}
	public List<String> getRoleOrgList() {
		return roleOrgList;
	}
	public void setRoleOrgList(List<String> roleOrgList) {
		this.roleOrgList = roleOrgList;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String[] getRoleOrgArray() {
		return roleOrgArray;
	}
	public void setRoleOrgArray(String[] roleOrgArray) {
		this.roleOrgArray = roleOrgArray;
	}
	public List<String> getUserSelectRoles() {
		return userSelectRoles;
	}
	public void setUserSelectRoles(List<String> userSelectRoles) {
		this.userSelectRoles = userSelectRoles;
	}
	/**
	 * @return the newPwd
	 */
	public String getNewPwd() {
		return newPwd;
	}
	/**
	 * @param newPwd the newPwd to set
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
}
