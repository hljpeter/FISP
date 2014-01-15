package com.synesoft.ftzmis.app.model;

import com.synesoft.ftzmis.domain.model.FtzActMstr;

public class FTZCMAccountQryForm {
	
	private FtzActMstr ftzActMstr;
	
	private String query_branchId = "";
	
	private String query_accountName = "";
	
	private String query_accountNo = "";
	
	private String query_subAccountNo = "";
	
	private String query_currency = "";
	
	private String query_accType = "";
	
	private String query_accStatus = "";
	
	private String selected_actNo = "";
	
	private String selected_subActNo = "";
	
	public FtzActMstr getFtzActMstr() {
		return ftzActMstr;
	}

	public void setFtzActMstr(FtzActMstr ftzActMstr) {
		this.ftzActMstr = ftzActMstr;
	}

	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_accountName() {
		return query_accountName;
	}

	public void setQuery_accountName(String query_accountName) {
		this.query_accountName = query_accountName;
	}

	public String getQuery_accountNo() {
		return query_accountNo;
	}

	public void setQuery_accountNo(String query_accountNo) {
		this.query_accountNo = query_accountNo;
	}

	public String getQuery_subAccountNo() {
		return query_subAccountNo;
	}

	public void setQuery_subAccountNo(String query_subAccountNo) {
		this.query_subAccountNo = query_subAccountNo;
	}

	public String getQuery_currency() {
		return query_currency;
	}

	public void setQuery_currency(String query_currency) {
		this.query_currency = query_currency;
	}

	public String getQuery_accType() {
		return query_accType;
	}

	public void setQuery_accType(String query_accType) {
		this.query_accType = query_accType;
	}

	public String getQuery_accStatus() {
		return query_accStatus;
	}

	public void setQuery_accStatus(String query_accStatus) {
		this.query_accStatus = query_accStatus;
	}

	public String getSelected_actNo() {
		return selected_actNo;
	}

	public void setSelected_actNo(String selected_actNo) {
		this.selected_actNo = selected_actNo;
	}

	public String getSelected_subActNo() {
		return selected_subActNo;
	}

	public void setSelected_subActNo(String selected_subActNo) {
		this.selected_subActNo = selected_subActNo;
	}
}
