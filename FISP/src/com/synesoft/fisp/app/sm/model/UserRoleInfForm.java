package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.UserRoleInf;


public class UserRoleInfForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	
	private String username;
	
	private String orgid;
	
	private UserRoleInf userRoleInf;
	
	/**
	 * check must input items
	 * @author michelle.wang
	 *
	 */
	public static interface SM_04_03_Modify {
	}
	@Valid
	@NotEmpty(groups = { SM_04_03_Modify.class}, message ="{e.sm.1011}")
	private List<String>  userRoleInfArr;
	
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
	 * @return the userRoleInf
	 */
	public UserRoleInf getUserRoleInf() {
		return userRoleInf;
	}

	/**
	 * @param userRoleInf the userRoleInf to set
	 */
	public void setUserRoleInf(UserRoleInf userRoleInf) {
		this.userRoleInf = userRoleInf;
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

}
