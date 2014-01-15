package com.synesoft.fisp.domain.model.vo;

import java.util.List;

import com.synesoft.fisp.domain.model.UserOrgInf;

/**
 * @author yyw
 *
 */
public class UserOrgVO extends UserOrgInf {

	private List<String> notInList;

	/**
	 * @return the notInList
	 */
	public List<String> getNotInList() {
		return notInList;
	}

	/**
	 * @param notInList the notInList to set
	 */
	public void setNotInList(List<String> notInList) {
		this.notInList = notInList;
	}
	
	
}
