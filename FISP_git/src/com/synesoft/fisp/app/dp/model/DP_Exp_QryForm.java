package com.synesoft.fisp.app.dp.model;

public class DP_Exp_QryForm {
	
	private String query_branchId = "";

	private String query_tableName = "";

	private String query_fileName = "";
	
	private String del_ExpId = "";

	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_tableName() {
		return query_tableName;
	}

	public void setQuery_tableName(String query_tableName) {
		this.query_tableName = query_tableName;
	}

	public String getQuery_fileName() {
		return query_fileName;
	}

	public void setQuery_fileName(String query_fileName) {
		this.query_fileName = query_fileName;
	}

	public String getDel_ExpId() {
		return del_ExpId;
	}

	public void setDel_ExpId(String del_ExpId) {
		this.del_ExpId = del_ExpId;
	}
	
}
