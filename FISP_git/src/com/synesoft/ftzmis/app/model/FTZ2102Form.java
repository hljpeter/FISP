package com.synesoft.ftzmis.app.model;

public class FTZ2102Form {
        
    private String query_branchId ="";
	
	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_submitDate_start() {
		return query_submitDate_start;
	}

	public void setQuery_submitDate_start(String query_submitDate_start) {
		this.query_submitDate_start = query_submitDate_start;
	}

	public String getQuery_submitDate_end() {
		return query_submitDate_end;
	}

	public void setQuery_submitDate_end(String query_submitDate_end) {
		this.query_submitDate_end = query_submitDate_end;
	}

	public String getQuery_msgId() {
		return query_msgId;
	}

	public void setQuery_msgId(String query_msgId) {
		this.query_msgId = query_msgId;
	}

	public String getQuery_msgStatus() {
		return query_msgStatus;
	}

	public void setQuery_msgStatus(String query_msgStatus) {
		this.query_msgStatus = query_msgStatus;
	}

	public String getQuery_accountNo() {
		return query_accountNo;
	}

	public void setQuery_accountNo(String query_accountNo) {
		this.query_accountNo = query_accountNo;
	}

	public String getQuery_accountName() {
		return query_accountName;
	}

	public void setQuery_accountName(String query_accountName) {
		this.query_accountName = query_accountName;
	}

	private String query_submitDate_start ="";
	
	private String query_submitDate_end ="";
	
	private String query_msgId ="";
	
	private String query_msgStatus ="";
	
	private String query_accountNo ="";
	
	private String query_accountName ="";
	
	private String query_currency="";
	
	public String getQuery_currency() {
		return query_currency;
	}

	public void setQuery_currency(String query_currency) {
		this.query_currency = query_currency;
	}

	public String getQuery_balanceCode() {
		return query_balanceCode;
	}

	public void setQuery_balanceCode(String query_balanceCode) {
		this.query_balanceCode = query_balanceCode;
	}

	public String getQuery_accOrgCode() {
		return query_accOrgCode;
	}

	public void setQuery_accOrgCode(String query_accOrgCode) {
		this.query_accOrgCode = query_accOrgCode;
	}

	private String query_balanceCode="";
	
	private String query_accOrgCode="";
	
    public String getSelected_msgId() {
		return selected_msgId;
	}

	public void setSelected_msgId(String selected_msgId) {
		this.selected_msgId = selected_msgId;
	}

	public Integer getSelected_seqNo() {
		return selected_seqNo;
	}

	public void setSelected_seqNo(Integer selected_seqNo) {
		this.selected_seqNo = selected_seqNo;
	}

	public String getSelected_msgNo() {
		return selected_msgNo;
	}

	public void setSelected_msgNo(String selected_msgNo) {
		this.selected_msgNo = selected_msgNo;
	}

	private String selected_msgId ="";
	
	private Integer selected_seqNo;
	
	private String selected_msgNo ="";
}
