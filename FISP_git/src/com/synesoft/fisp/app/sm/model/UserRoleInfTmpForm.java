package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.UserRoleInfTmp;

public class UserRoleInfTmpForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserRoleInfTmp userRoleInfTmp;

	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_04_02_Add {
	}
	@Valid
	@NotEmpty(groups = { SM_04_02_Add.class}, message ="{e.sm.1006}")
	private String userid;
	private String username;
	private String orgid;
	@Valid
	@NotEmpty(groups = { SM_04_02_Add.class}, message ="{e.sm.1012}")
	private String userorgid;
	@Valid
	@NotEmpty(groups = { SM_04_02_Add.class}, message ="{e.sm.1011}")
	private List<String>  userRoleInfArr;
	private String opttype;
	/**
	 * @return the userRoleInfTmp
	 */
	public UserRoleInfTmp getUserRoleInfTmp() {
		return userRoleInfTmp;
	}
	/**
	 * @param userRoleInfTmp the userRoleInfTmp to set
	 */
	public void setUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp) {
		this.userRoleInfTmp = userRoleInfTmp;
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
	 * @return the userorgid
	 */
	public String getUserorgid() {
		return userorgid;
	}
	/**
	 * @param userorgid the userorgid to set
	 */
	public void setUserorgid(String userorgid) {
		this.userorgid = userorgid;
	}
	/**
	 * @return the userRoleInfArr
	 */
	public List<String> getUserRoleInfArr() {
		return userRoleInfArr;
	}
	/**
	 * @param userRoleInfArr the userRoleInfArr to set
	 */
	public void setUserRoleInfArr(List<String> userRoleInfArr) {
		this.userRoleInfArr = userRoleInfArr;
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
	
}
