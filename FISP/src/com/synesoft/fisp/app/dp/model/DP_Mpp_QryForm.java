package com.synesoft.fisp.app.dp.model;

public class DP_Mpp_QryForm {
	
	private String query_branchId = "";

	private String query_destTable = "";

	private String query_srcTable = "";

	private String query_procType = "";

	private String query_mppName = "";
	
	private String del_MppId = "";

	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_destTable() {
		return query_destTable;
	}

	public void setQuery_destTable(String query_destTable) {
		this.query_destTable = query_destTable;
	}

	public String getQuery_srcTable() {
		return query_srcTable;
	}

	public void setQuery_srcTable(String query_srcTable) {
		this.query_srcTable = query_srcTable;
	}

	public String getQuery_procType() {
		return query_procType;
	}

	public void setQuery_procType(String query_procType) {
		this.query_procType = query_procType;
	}

	public String getQuery_mppName() {
		return query_mppName;
	}

	public void setQuery_mppName(String query_mppName) {
		this.query_mppName = query_mppName;
	}

	public String getDel_MppId() {
		return del_MppId;
	}

	public void setDel_MppId(String del_MppId) {
		this.del_MppId = del_MppId;
	}
	
	

}
